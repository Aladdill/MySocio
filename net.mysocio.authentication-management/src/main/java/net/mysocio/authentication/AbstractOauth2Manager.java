/**
 * 
 */
package net.mysocio.authentication;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.accounts.Account;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;


/**
 * @author Aladdin
 *
 */
public abstract class AbstractOauth2Manager implements IAuthenticationManager {

	protected String getBasicRequestUrl(){
		return AuthenticationResourcesManager.getAuthenticationRequestUrl(getUserIdentifier());
	}
	
	protected abstract UserIdentifier getUserIdentifier();

	protected String getMysocioId(){
		return AuthenticationResourcesManager.getAuthenticationId(getUserIdentifier());
	}

	protected String getMysocioRedirect(){
		return "http://mysocio.sytes.net/mysocio/login";
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

	protected abstract Account getAccount(OAuthService service, Token accessToken) throws Exception;

	protected abstract Logger getLogger();

	public Account login(IConnectionData connectionData) throws Exception {
		String code = connectionData.getRequestParameter("code");
		Verifier verifier = new Verifier(code);
		OAuthService service = getService();
		Token accessToken = service.getAccessToken(null, verifier);
		return getAccount(service, accessToken);
	}

//	private void addUrlParameter(StringBuffer parameters, String parameter,
//			String value) throws UnsupportedEncodingException {
//		parameters.append(parameter).append("=").append(URLEncoder.encode(value , "UTF-8")).append("&");
//	}
//	private void addLastUrlParameter(StringBuffer parameters, String parameter,
//			String value) throws UnsupportedEncodingException {
//		parameters.append(parameter).append("=").append(URLEncoder.encode(value , "UTF-8"));
//	}
}
