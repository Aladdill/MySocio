/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;


/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookFriend extends NamedFacebookObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1091320712068342010L;
}
