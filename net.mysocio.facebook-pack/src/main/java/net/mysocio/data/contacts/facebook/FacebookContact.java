/**
 * 
 */
package net.mysocio.data.contacts.facebook;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.contacts.Contact;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookContact extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405708860373380037L;

}
