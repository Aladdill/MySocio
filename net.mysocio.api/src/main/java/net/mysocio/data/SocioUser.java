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

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.Destination;
import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 * Although User is subclass of MySocio object, tags list is private list of tags usable by this user and not object tags.
 */
@PersistenceCapable(detachable="true")
public class SocioUser extends NamedObject{
	public static final String ALL_SOURCES = "All";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2886854604233072581L;
	private Map<String, List<String>> unreadMessages = new HashMap<String, List<String>>();
	private List<Account> accounts = new ArrayList<Account>();
	private Account mainAccount;
	private List<SocioContact> contacts = new ArrayList<SocioContact>();
	private List<Source> sources = new ArrayList<Source>();
	private List<Destination> destinations = new ArrayList<Destination>();
	private Map<String, SocioTag> userTags = new HashMap<String, SocioTag>();
	private Long lastUpdate = 0l;
	private Integer totalUnreadmessages = 0;	
	
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
	
	public List<SocioContact> getContacts(){
		return contacts;
	}

	public List<String> getUnreadMessages(Set<String> tagsIds){
		Set<String> unreaddenMessages = new HashSet<String>();
		for (String tagId : tagsIds) {
			List<String> messages = this.unreadMessages.get(tagId);
			if (messages != null){
				
				unreaddenMessages.addAll(messages);
			}
		}
		return new ArrayList<String>(unreaddenMessages);
	}
	
	public Integer getUnreadMessagesNum(String id){
		List<String> messages = this.unreadMessages.get(id);
		if (messages == null || messages.isEmpty()){
			return 0;
		}
		return messages.size();
	}
	
	public int addUnreadMessages(String sourceId, List<String> messages){
		List<String> list = unreadMessages.get(sourceId);
		if (list == null){
			list = new ArrayList<String>();
		}
		messages.removeAll(list);
		list.addAll(messages);
		unreadMessages.put(sourceId, list);
		return messages.size();
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
	
	public void setMessageReadden(String messageId){
		Set<String> tags = unreadMessages.keySet();
		for (String tagId : tags) {
			List<String> messages = unreadMessages.get(tagId);
			messages.remove(messageId);
			unreadMessages.put(tagId, messages);
		}
		totalUnreadmessages--;
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
		if (!getAccounts().contains(account)){
			getAccounts().add(account);
			this.addTags(account.getTags());
		}
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<SocioContact> contacts) {
		this.contacts = contacts;
	}
	public void addContact(SocioContact contact) {
		this.contacts.add(contact);
	}

	public List<String> getAllUnreadMessages() {
		return getUnreadMessages(userTags.keySet());
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
		SocioTag currentTag = userTags.get(tag.getId());
		if (currentTag != null){
			tag.setRefCount(currentTag.getRefCount() + 1);
		}else{
			//init reference counter of tag
			tag.setRefCount(1);
		}
		userTags.put(tag.getId(), tag);
	}
	
	public void removeTag(SocioTag tag) throws CorruptedDataException{
		String tagId = tag.getId();
		SocioTag socioTag = userTags.get(tagId);
		if (socioTag == null){
			throw new CorruptedDataException("Trying to remove uexisting tag from User: " + tag.getValue());
		}
		tag.setRefCount(tag.getRefCount()-1);
		if (tag.getRefCount() <= 0){
			userTags.remove(tagId);
		}
	}
	
	public void removeTags(List<SocioTag> tags) throws CorruptedDataException{
		for (SocioTag tag : tags) {
			removeTag(tag);
		}
	}
	
	public void addTags(List<SocioTag> tags){
		for (SocioTag tag : tags) {
			addTag(tag);
		}
	}
	
	public void addSource(Source source) {
		if (!getSources().contains(source)){
			getSources().add(source);
			this.addTags(source.getTags());
		}
	}
	
	public Source removeSource(String id) throws CorruptedDataException{
		Iterator<Source> iterator = sources.iterator();
		while (iterator.hasNext()){
			Source source = iterator.next();
			if (source.getId().equals(id)){
				iterator.remove();
				removeTags(source.getTags());
				return source;
			}
		}
		return null;
	}

	public void addSources(List<? extends Source> sources) {
		for (Source source : sources) {
			addSource(source);
		}
	}

	public List<Source> getSources() {
		return sources;
	}
	
	public List<Destination> getDestinations() {
		return destinations;
	}
	
	public void addDestination(Destination destination) {
		destinations.add(destination);
	}

	public void addDestinations(List<? extends Destination> destinations) {
		this.destinations.addAll(destinations);		
	}

	public List<SocioTag> getDefaultTags() {
		return Collections.emptyList();
	}

	public Integer getTotalUnreadmessages() {
		return totalUnreadmessages;
	}
	
	public void addTotalUnreadmessages(int num) {
		totalUnreadmessages += num;
	}

	public Account getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(Account mainAccount) {
		this.mainAccount = mainAccount;
	}
}
