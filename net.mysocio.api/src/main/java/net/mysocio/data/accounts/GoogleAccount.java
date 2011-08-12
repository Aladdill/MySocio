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
public class GoogleAccount extends Oauth2Account{
	private static final String ACCOUNT_TYPE = "google";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707109361608748417L;
	
	private String refreshToken;
	
	public GoogleAccount() {
		super();
	}

	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}
}
