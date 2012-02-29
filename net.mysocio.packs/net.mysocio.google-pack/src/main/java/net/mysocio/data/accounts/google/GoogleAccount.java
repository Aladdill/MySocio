/**
 * 
 */
package net.mysocio.data.accounts.google;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.Oauth2Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
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
		return null;
	}
	
	public List<SocioTag> getDefaultTags() {
		List<SocioTag> tags = new ArrayList<SocioTag>();
		SocioTag tag = new SocioTag();
		tag.setValue("google.tag");
		tag.setIconType("google.icon.general");
		tags.add(tag);
		SocioTag accTag = new SocioTag();
		accTag.setValue(getUserName());
		accTag.setIconType("google.icon.account");
		tags.add(accTag);
		return tags;
	}

	@Override
	public String getIconUrl() {
		return "google.icon.account";
	}
}
