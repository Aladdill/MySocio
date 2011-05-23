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
import javax.jdo.annotations.Serialized;
import javax.jdo.annotations.Value;

import net.mysocio.connection.readers.ISourcesGroup;
import net.mysocio.connection.readers.SourcesGroup;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
public class SocioUser extends Contact implements IUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2886854604233072581L;
	@Join
	@Key(types=java.lang.String.class)
    @Value(types=UnreaddenMessages.class)
    @Persistent
    @Serialized
	private Map<String, UnreaddenMessages> unreadMessages = new HashMap<String, UnreaddenMessages>();
	@Join
	@Persistent(types={Account.class},mappedBy = "id")
	private List<Account> accounts = new ArrayList<Account>();
	@Join
	@Persistent(types={SourcesGroup.class},mappedBy = "id")
	private List<ISourcesGroup> sourcesGroups = new ArrayList<ISourcesGroup>();
	@Join
	@Persistent(types={SocioContact.class},mappedBy = "id")
	private List<IContact> contacts = new ArrayList<IContact>();
	@Persistent
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

	public UnreaddenMessages getUnreadMessages(String sourceId){
		return unreadMessages.get(sourceId);
	}
	
	public void addUnreadMessages(String sourceId, UnreaddenMessages messages){
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

	public Map<String, UnreaddenMessages> getUnreadMessages() {
		return unreadMessages;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
