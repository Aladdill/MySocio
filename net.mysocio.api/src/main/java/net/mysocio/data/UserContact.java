/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.contacts.Contact;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author DH67CL
 *
 */
@Entity("my_socio_user_contacts")
public class UserContact extends UserObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5303728949961569122L;
	@Reference
	private List<Contact> contacts = new ArrayList<Contact>();
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
