/**
 * 
 */
package net.mysocio.data.accounts;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookAccount extends Oauth2Account {

	private static final String ACCOUNT_TYPE = "facebook";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6186811184252889740L;

	/* (non-Javadoc)
	 * @see net.mysocio.data.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}
}
