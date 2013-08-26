/**
 * 
 */
package net.mysocio.data.contacts;

import java.util.List;

import net.mysocio.data.NamedObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("contacts_group")
public abstract class ContactsGroup extends NamedObject{
	private List<String> contacts;

	/**
	 * 
	 */
	private static final long serialVersionUID = 9149264020710533556L;

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}
	
	public void addContact(String contact) {
		this.contacts.add(contact);
	}
}
