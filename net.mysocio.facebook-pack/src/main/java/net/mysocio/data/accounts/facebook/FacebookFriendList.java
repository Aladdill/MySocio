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
public class FacebookFriendList extends NamedFacebookObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3949039036481784242L;
}