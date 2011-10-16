/**
 * 
 */
package net.mysocio.data.messages.facebook;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.GeneralMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookMessage extends GeneralMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5110172305702339723L;

}
