/**
 * 
 */
package net.mysocio.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import net.mysocio.connection.readers.ISourcesGroup;
import net.mysocio.connection.readers.SourcesGroup;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Entity(name="users")
public class SocioUser extends Contact implements IUser {
	@ElementCollection()
	private Map<Long, UnreaddenMessages> unreadMessages = new HashMap<Long, UnreaddenMessages>();
	@ElementCollection()
	private Set<Account> accounts = new HashSet<Account>();
	@ManyToMany(targetEntity = SourcesGroup.class)
	private Set<ISourcesGroup> sourcesGroups = new HashSet<ISourcesGroup>();
	@ManyToMany(targetEntity = SocioContact.class)
	private Set<IContact> contacts = new HashSet<IContact>();
	
	public Set<IContact> getContacts(){
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

	public Set<ISourcesGroup> getSourcesGroups() {
		return this.sourcesGroups;
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

	public Map<Long, UnreaddenMessages> getUnreadMessages() {
		return unreadMessages;
	}
}
