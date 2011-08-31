/**
 * 
 */
package net.mysocio.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.contacts.IContact;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 * Although User is subclass of MySocio object, tags list is private list of tags usable by this user and not object tags.
 */
@PersistenceCapable
public class SocioUser extends Contact implements IUser {
	public static final String ALL_SOURCES = "All";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2886854604233072581L;
    @Persistent
	private Map<String, Set<IMessage>> unreadMessages = new HashMap<String, Set<IMessage>>();
	@Persistent
	private Set<Account> accounts = new HashSet<Account>();
	@Persistent
	private Set<IContact> contacts = new HashSet<IContact>();
	@Persistent
	private Map<String, SocioTag> userTags = new HashMap<String, SocioTag>();
	@Persistent
	private Long lastUpdate = 0l;
	@NotPersistent
	private Map<String, Set<ISource>> sortedSources;
	
	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getSelectedSource() {
		return selectedSource;
	}

	public void setSelectedSource(String selectedSource) {
		this.selectedSource = selectedSource;
	}

	@Persistent
	private String selectedSource = ALL_SOURCES;
	
	/**
	 * @return the sortedSources
	 */
	public Map<String, Set<ISource>> getSortedSources() {
		if (sortedSources == null){
			sortedSources = new HashMap<String, Set<ISource>>();
			sortedSources = initSortedSources();
		}
		return sortedSources;
	}

	private Map<String, Set<ISource>> initSortedSources() {
		for (ISource source : getSources()) {
			addSourceToSortedSources(source);
		}
		return sortedSources;
	}

	/**
	 * @param source
	 */
	private void addSourceToSortedSources(ISource source) {
		Set<SocioTag> tags = source.getTags();
		for (SocioTag tag : tags) {
			Set<ISource> sources = sortedSources.get(tag);
			if (sources == null){
				sources = new HashSet<ISource>();
				sortedSources.put(tag.getValue(), sources);
			}
			sources.add(source);
		}
	}

	private String locale;
	
	public Set<IContact> getContacts(){
		return contacts;
	}

	public Set<IMessage> getUnreadMessages(Set<ISource> sources){
		Set<IMessage> unreaddenMessages = new HashSet<IMessage>();
		for (ISource source : sources) {
			Set<IMessage> messages = this.unreadMessages.get(source.getId());
			if (messages != null){
				unreaddenMessages.addAll(messages);
			}
		}
		return unreaddenMessages;
	}
	
	public Integer getUnreadMessagesNum(String id){
		Set<IMessage> messages = this.unreadMessages.get(id);
		if (messages == null || messages.isEmpty()){
			return 0;
		}
		return messages.size();
	}
	
	public void addUnreadMessages(String sourceId, Set<IMessage> messages){
		Set<IMessage> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new HashSet<IMessage>();
		}
		list.addAll(messages);
		unreadMessages.put(sourceId, list);
	}
	
	public void addUnreadMessage(IMessage message){
		String sourceId = message.getSourceId();
		Set<IMessage> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new HashSet<IMessage>();
		}
		list.add(message);
		unreadMessages.put(sourceId, list);
	}
	
	public void setMessageReadden(IMessage message) throws CorruptedDataException{
		String sourceId = message.getSourceId();
		Set<IMessage> list = unreadMessages.get(sourceId);
		if (list == null){
			throw new CorruptedDataException();
		}
		list.remove(message);
	}

	/**
	 * @return the accounts
	 */
	public Set<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(Set<IContact> contacts) {
		this.contacts = contacts;
	}
	public void addContact(IContact contact) {
		this.contacts.add(contact);
	}

	public Set<IMessage> getAllUnreadMessages() {
		return getUnreadMessages(getSources());
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Set<IMessage> getUnreadMessages() {
		if (selectedSource.equalsIgnoreCase(SocioUser.ALL_SOURCES)){
			return getAllUnreadMessages();
		}
		
		return getUnreadMessages(getSortedSources().get(selectedSource));
	}

	public Map<String, SocioTag> getUserTags() {
		return userTags;
	}
	
	public SocioTag getTag(String value) {
		return userTags.get(value);
	}
	
	public void addTag(SocioTag tag){
		userTags.put(tag.getValue(), tag);
	}
}
