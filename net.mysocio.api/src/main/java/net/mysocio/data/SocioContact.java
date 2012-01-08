/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.contacts.IContact;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class SocioContact extends NamedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8736689433772607760L;
	
	private List<IContact> accountsContacts = new ArrayList<IContact>();

	public List<IContact> getAccountsContacts() {
		return accountsContacts;
	}

	public void setAccountsContacts(List<IContact> accountsContacts) {
		this.accountsContacts = accountsContacts;
	}
}
