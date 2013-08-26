/**
 * 
 */
package net.mysocio.data;

import java.util.List;

import net.mysocio.data.contacts.Contact;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author DH67CL
 *
 */
@Entity("contacts_lists")
public class ContactsList extends NamedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6474560082804458229L;
	@Reference
	private List<Contact> contacts;
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
	public void addContact(Contact contact){
		this.contacts.add(contact);
	}
}
