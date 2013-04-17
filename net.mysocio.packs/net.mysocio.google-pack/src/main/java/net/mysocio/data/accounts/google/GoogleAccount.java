/**
 * 
 */
package net.mysocio.data.accounts.google;

import java.util.Collections;
import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Oauth2Account;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("accounts")
public class GoogleAccount extends Oauth2Account{
	public static final String ACCOUNT_TYPE = "google";

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

	@Override
	public List<Source> getSources() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}
	
	@Override
	public String getIconUrl() {
		return "google.icon.account";
	}

	@Override
	public SocioTag createAccountTagset(UserTags userTags) {
		return createAccountTypeTag(userTags);
	}
}
