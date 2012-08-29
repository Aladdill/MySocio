/**
 * 
 */
package net.mysocio.data.accounts;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.ContactsList;
import net.mysocio.data.IDisplayedObject;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioUser;
import net.mysocio.data.contacts.Contact;
import net.mysocio.ui.data.objects.NewAccountLine;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author Aladdin
 *
 */
@Entity("accounts")
public abstract class Account extends SocioObject implements IDisplayedObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7561557374532619473L;
	private String userName =  new String();
	private String accountUniqueId = new String();
	@Reference
	private SocioUser user;
	private String userpicUrl = new String();
	private String email = new String();
	@Reference
	private List<Contact> contacts = new ArrayList<Contact>();
	@Reference
	private List<ContactsList> contactsLists = new ArrayList<ContactsList>();
	

	public SocioUser getUser() {
		return user;
	}

	public void setUser(SocioUser user) {
		this.user = user;
	}

	public String getAccountType(){return null;}

	public String getAccountUniqueId() {
		return accountUniqueId;
	}

	public void setAccountUniqueId(String accountUniqueId) {
		this.accountUniqueId = accountUniqueId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserpicUrl() {
		return userpicUrl;
	}

	public void setUserpicUrl(String userpicUrl) {
		this.userpicUrl = userpicUrl;
	}
	
	public List<Source> getSources(){return null;}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIconUrl(){return null;}
	
	@Override
	public String getUiCategory() {
		return NewAccountLine.CATEGORY;
	}

	@Override
	public String getUiName() {
		return NewAccountLine.NAME;
	}

	/**
	 * @return the contacts
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public void addContact(Contact contact) {
		this.contacts.add(contact);
	}

	/**
	 * @return the contactsLists
	 */
	public List<ContactsList> getContactsLists() {
		return contactsLists;
	}

	/**
	 * @param contactsLists the contactsLists to set
	 */
	public void setContactsLists(List<ContactsList> contactsLists) {
		this.contactsLists = contactsLists;
	}
	public void addContactsList(ContactsList contactsList) {
		this.contactsLists.add(contactsList);
	}
}
