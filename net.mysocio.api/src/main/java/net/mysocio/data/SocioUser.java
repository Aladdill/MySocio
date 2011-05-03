/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Value;

import net.mysocio.connection.readers.ISourcesGroup;
import net.mysocio.connection.readers.SourcesGroup;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
public class SocioUser extends Contact implements IUser {
	@Join
	@Key(types=java.lang.Long.class)
    @Value(types=UnreaddenMessages.class)
	private Map<Long, UnreaddenMessages> unreadMessages = new HashMap<Long, UnreaddenMessages>();
	@Join
	@Persistent(types={Account.class},mappedBy = "id")
	private List<Account> accounts = new ArrayList<Account>();
	@Join
	@Persistent(types={SourcesGroup.class},mappedBy = "id")
	private List<ISourcesGroup> sourcesGroups = new ArrayList<ISourcesGroup>();
	@Join
	@Persistent(types={SocioContact.class},mappedBy = "id")
	private List<IContact> contacts = new ArrayList<IContact>();
	
	private UserIdentifier userIdentifier;
	
	public UserIdentifier getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(UserIdentifier userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	private String locale;
	
	public List<IContact> getContacts(){
		return contacts;
	}

	public UnreaddenMessages getUnreadMessages(Long sourceId){
		return unreadMessages.get(sourceId);
	}
	
	public void addUnreadMessages(Long sourceId, UnreaddenMessages messages){
		unreadMessages.put(sourceId, messages);
	}

	public void addSourceGroups(Set<? extends ISourcesGroup> sourcesGroups) {
		this.sourcesGroups.addAll(sourcesGroups);		
	}

	public void addSourcesGroups(ISourcesGroup SourcesGroup) {
		this.sourcesGroups.add(SourcesGroup);		
	}

	public List<ISourcesGroup> getSourcesGroups() {
		return this.sourcesGroups;
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

	public Map<Long, UnreaddenMessages> getUnreadMessages() {
		return unreadMessages;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
