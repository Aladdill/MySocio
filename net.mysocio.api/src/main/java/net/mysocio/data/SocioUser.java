/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.contacts.IContact;

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
	private Map<String, List<String>> unreadMessages = new HashMap<String, List<String>>();
	@Persistent
	private List<Account> accounts = new ArrayList<Account>();
	@Persistent
	private List<IContact> contacts = new ArrayList<IContact>();
	@Persistent
	private Map<String, SocioTag> userTags = new HashMap<String, SocioTag>();
	@Persistent
	private Long lastUpdate = 0l;
	
	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getSelectedSource() {
		return selectedTag;
	}

	public void setSelectedSource(String selectedSource) {
		this.selectedTag = selectedSource;
	}

	@Persistent
	private String selectedTag = ALL_SOURCES;
	
	private String locale;
	
	public List<IContact> getContacts(){
		return contacts;
	}

	public List<String> getUnreadMessages(List<ISource> sources){
		List<String> unreaddenMessages = new ArrayList<String>();
		for (ISource source : sources) {
			List<String> messages = this.unreadMessages.get(source.getId());
			if (messages != null){
				unreaddenMessages.addAll(messages);
			}
		}
		return unreaddenMessages;
	}
	
	public Integer getUnreadMessagesNum(String id){
		List<String> messages = this.unreadMessages.get(id);
		if (messages == null || messages.isEmpty()){
			return 0;
		}
		return messages.size();
	}
	
	public void addUnreadMessages(String sourceId, List<String> messages){
		List<String> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new ArrayList<String>();
		}
		messages.removeAll(list);
		list.addAll(messages);
		unreadMessages.put(sourceId, list);
	}
	
	public void addUnreadMessage(String sourceId, String messageId){
		List<String> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new ArrayList<String>();
		}
		if (!list.contains(messageId)){
			list.add(messageId);
		}
		unreadMessages.put(sourceId, list);
	}
	
	public void setMessageReadden(String sourceId, String messageId){
		List<String> sourceList = unreadMessages.remove(sourceId);
		sourceList.remove(messageId);
		unreadMessages.put(sourceId, sourceList);
	}

	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<IContact> contacts) {
		this.contacts = contacts;
	}
	public void addContact(IContact contact) {
		this.contacts.add(contact);
	}

	public List<String> getAllUnreadMessages() {
		return getUnreadMessages(getSources());
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public List<String> getUnreadMessages() {
		if (selectedTag.equalsIgnoreCase(SocioUser.ALL_SOURCES)){
			return getAllUnreadMessages();
		}
		List<String> messages = null;
		messages = unreadMessages.get(selectedTag);
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
	
	public void addTags(List<SocioTag> tags){
		for (SocioTag tag : tags) {
			addTag(tag);
		}
	}

	@Override
	public void addSource(ISource source) {
		super.addSource(source);
	}

	@Override
	public void addSources(List<? extends ISource> sources) {
		for (ISource source : sources) {
			addSource(source);
		}
	}

	public List<SocioTag> getDefaultTags() {
		return Collections.emptyList();
	}
}
