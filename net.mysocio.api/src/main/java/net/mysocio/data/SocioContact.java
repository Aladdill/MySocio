/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.contacts.Contact;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SocioContact extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8736689433772607760L;

}
