/**
 * 
 */
package net.mysocio.authentication;

import java.util.Date;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.MySocioInfo;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.ui.management.UnapprovedUserException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Aladdin
 *
 */
public abstract class AbstractOauth2Manager implements IAuthenticationManager {
	private static final Logger logger = LoggerFactory.getLogger(AbstractOauth2Manager.class);

//	protected String getBasicRequestUrl(){
//		return AuthenticationResourcesManager.getAuthenticationRequestUrl(getUserIdentifier());
//	}
	
	protected abstract String getUserIdentifier();

	protected String getMysocioId(){
		return AuthenticationResourcesManager.getAuthenticationId(getUserIdentifier());
	}

	protected String getMysocioRedirect(){
		return AuthenticationResourcesManager.getRedirectUrl();
	}

	protected String getScope(){
		return AuthenticationResourcesManager.getAuthenticationScope(getUserIdentifier());
	}

	public String getRequestUrl() {
		OAuthService service = getService();
		return service.getAuthorizationUrl(null);
	}

	/**
	 * @return
	 */
	protected OAuthService getService() {
		OAuthService service = new ServiceBuilder()
        .provider(getApiClass())
        .apiKey(getMysocioId())
        .apiSecret(getMySocioSecret())
        .callback(getMysocioRedirect() + "&" + AccountsManager.IDENTIFIER + "=" + getUserIdentifier())
        .scope(getScope())
        .build();
		logger.debug("Id = " + getMysocioId());
		logger.debug("Id = " + getMySocioSecret());
		return service;
	}

	protected abstract Class<? extends Api> getApiClass();

	protected String getMySocioSecret(){
		return AuthenticationResourcesManager.getAuthenticationSecret(getUserIdentifier());
	}

//	protected String getTokenUrl(){
//		return AuthenticationResourcesManager.getAuthenticationTokenUrl(getUserIdentifier());
//	}

	protected abstract Account getAccount(Token accessToken) throws Exception;

	protected abstract Logger getLogger();

	public Account getAccount(IConnectionData connectionData) throws Exception {
		String code = connectionData.getSessionAttribute("code");
		Verifier verifier = new Verifier(code);
		OAuthService service = getService();
		Token accessToken = service.getAccessToken(null, verifier);
		return getAccount(accessToken);
	}

	protected void checkUserInvitation(String email, IDataManager dataManager)
			throws UnapprovedUserException {
		if (dataManager.getUserPermissions(email) == null){
			String message = "User with email " + email + "wasn't approved and knocked.";
			logger.error(message);
			try {
				dataManager.saveObject(new MySocioInfo("Unapproved User", message, new Date().toString()));
			} catch (Exception e) {
				logger.warn("Coudn't save info.",e);
			}
//			throw new UnapprovedUserException();
		}
		try {
			dataManager.saveObject(new MySocioInfo("Logged In User", "User with email " + email, new Date().toString()));
		} catch (Exception e) {
			logger.warn("Coudn't save info.",e);
		}
	}
}
