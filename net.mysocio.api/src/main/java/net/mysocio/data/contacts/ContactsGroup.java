/**
 * 
 */
package net.mysocio.data.contacts;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
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
