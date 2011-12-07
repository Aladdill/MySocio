/**
 * 
 */
package net.mysocio.authentication;

import java.rmi.AccessException;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.accounts.Account;
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

	protected String getBasicRequestUrl(){
		return AuthenticationResourcesManager.getAuthenticationRequestUrl(getUserIdentifier());
	}
	
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
        .callback(getMysocioRedirect())
        .scope(getScope())
        .build();
		return service;
	}

	protected abstract Class<? extends Api> getApiClass();

	protected String getMySocioSecret(){
		return AuthenticationResourcesManager.getAuthenticationSecret(getUserIdentifier());
	}

	protected String getTokenUrl(){
		return AuthenticationResourcesManager.getAuthenticationTokenUrl(getUserIdentifier());
	}

	protected abstract Account getAccount(Token accessToken) throws Exception;

	protected abstract Logger getLogger();

	public Account getAccount(IConnectionData connectionData) throws Exception {
		String error = connectionData.getRequestParameter("error");
		if (error != null && error.equals("access_denied")){
			logger.error("Access to user account wasn't granted.");
			throw new AccessException("Are you sure you want to log in?");
		}
		String code = connectionData.getRequestParameter("code");
		Verifier verifier = new Verifier(code);
		OAuthService service = getService();
		Token accessToken = service.getAccessToken(null, verifier);
		return getAccount(accessToken);
	}
}
