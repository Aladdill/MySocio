/**
 * 
 */
package net.mysocio.data.accounts.google;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.google.GoogleSource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Oauth2Account;
import net.mysocio.data.messages.GeneralMessage;

import com.github.jmkgreen.morphia.annotations.Entity;

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
		List<Source> sources = new ArrayList<Source>();
		GoogleSource source = new GoogleSource();
		source.setAccount(this);
		source.setName(getUserName());
		source.setUrl("http://graph.facebook.com/" + getAccountUniqueId());
		sources.add(source);
		return sources;
	}
	
	@Override
	public String getIconUrl() {
		return "google.icon.account";
	}

	@Override
	public SocioTag createAccountTagset(UserTags userTags) {
		SocioTag accountTypeTag = createAccountTypeTag(userTags);
		SocioTag sourceTag = userTags.createTag(getAccountUniqueId(), getUserName(), accountTypeTag);
		return accountTypeTag;
	}

	@Override
	public void postToAccount(String message) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void like(GeneralMessage message) throws Exception {
		// TODO Auto-generated method stub
	}
}
