/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
	private Map<String, List<IMessage>> unreadMessages = new HashMap<String, List<IMessage>>();
	@Persistent
	private Set<Account> accounts = new HashSet<Account>();
	@Persistent
	private Set<IContact> contacts = new HashSet<IContact>();
	@Persistent
	private Map<String, SocioTag> userTags = new HashMap<String, SocioTag>();
	@Persistent
	private Long lastUpdate = 0l;
	@NotPersistent
	private Map<SocioTag, Set<ISource>> sortedSources;
	
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
	public Map<SocioTag, Set<ISource>> getSortedSources() {
		if (sortedSources == null){
			sortedSources = new HashMap<SocioTag, Set<ISource>>();
			sortedSources = initSortedSources();
		}
		return sortedSources;
	}

	private Map<SocioTag, Set<ISource>> initSortedSources() {
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
				sortedSources.put(tag, sources);
			}
			sources.add(source);
		}
	}

	private String locale;
	
	public Set<IContact> getContacts(){
		return contacts;
	}

	public List<IMessage> getUnreadMessages(Set<ISource> sources){
		List<IMessage> unreaddenMessages = new ArrayList<IMessage>();
		for (ISource source : sources) {
			List<IMessage> messages = this.unreadMessages.get(source.getId());
			if (messages != null){
				unreaddenMessages.addAll(messages);
			}
		}
		return unreaddenMessages;
	}
	
	public Integer getUnreadMessagesNum(String id){
		List<IMessage> messages = this.unreadMessages.get(id);
		if (messages == null || messages.isEmpty()){
			return 0;
		}
		return messages.size();
	}
	
	public void addUnreadMessages(String sourceId, List<IMessage> messages){
		List<IMessage> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new ArrayList<IMessage>();
		}
		messages.removeAll(list);
		list.addAll(messages);
		unreadMessages.put(sourceId, list);
	}
	
	public void addUnreadMessage(IMessage message){
		String sourceId = message.getSourceId();
		List<IMessage> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new ArrayList<IMessage>();
		}
		if (!list.contains(message)){
			list.add(message);
		}
		unreadMessages.put(sourceId, list);
	}
	
	public void setMessageReadden(String messageId) throws CorruptedDataException{
		List<IMessage> list = getUnreadMessages();
		if (list == null){
			throw new CorruptedDataException();
		}
		Iterator<IMessage> iterator = list.iterator();
		IMessage message = null;
		while(iterator.hasNext()) {
			message = iterator.next();
			if (message.getId().equals(messageId)){
				break;
			}
		}
		if (message != null){
			List<IMessage> sourceList = unreadMessages.get(message.getSourceId());
			sourceList.remove(message);
		}
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

	public List<IMessage> getAllUnreadMessages() {
		return getUnreadMessages(getSources());
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public List<IMessage> getUnreadMessages() {
		if (selectedSource.equalsIgnoreCase(SocioUser.ALL_SOURCES)){
			return getAllUnreadMessages();
		}
		List<IMessage> messages = null; 
		SocioTag tag = getTag(selectedSource);
		if (tag != null){
			messages = getUnreadMessages(getSortedSources().get(tag));
		}else{
			messages = unreadMessages.get(selectedSource);
		}
		if (messages == null){
			return Collections.emptyList();
		}
		return messages;
	}

	public Map<String, SocioTag> getUserTags() {
		return userTags;
	}
	
	public SocioTag getTag(String id) {
		return userTags.get(id);
	}
	
	public void addTag(SocioTag tag){
		userTags.put(tag.getId(), tag);
	}
	
	public void addTags(Set<SocioTag> tags){
		for (SocioTag tag : tags) {
			addTag(tag);
		}
	}

	@Override
	public void addSource(ISource source) {
		addTags(source.getTags());
		super.addSource(source);
	}

	@Override
	public void addSources(List<? extends ISource> sources) {
		for (ISource source : sources) {
			addSource(source);
		}
	}
}
