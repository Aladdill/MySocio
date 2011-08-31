/**
 * 
 */
package net.mysocio.data.messages;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

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
