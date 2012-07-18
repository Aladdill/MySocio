/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

import net.mysocio.data.contacts.Contact;



/**
 * @author Aladdin
 *
 */
@Entity("my_socio_contacts")
public class SocioContact extends NamedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8736689433772607760L;
	@Reference
	private List<Contact> accountsContacts = new ArrayList<Contact>();

	public List<Contact> getAccountsContacts() {
		return accountsContacts;
	}

	public void setAccountsContacts(List<Contact> accountsContacts) {
		this.accountsContacts = accountsContacts;
	}
}
