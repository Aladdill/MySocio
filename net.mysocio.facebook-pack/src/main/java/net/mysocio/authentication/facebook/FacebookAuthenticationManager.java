/**
 * 
 */
package net.mysocio.authentication.facebook;

import java.util.Set;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.facebook.FacebookAccount;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class FacebookAuthenticationManager extends AbstractOauth2Manager{
	private static final Logger logger = LoggerFactory.getLogger(FacebookAuthenticationManager.class);
	
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUserIdentifier() {
		return "facebook";
	}


	@Override
	protected Class<? extends Api> getApiClass() {
		return FacebookApi.class;
	}

	@Override
	protected Account getAccount(OAuthService service, Token accessToken)
			throws Exception {
		String response = callUrl(service, accessToken, "https://graph.facebook.com/me");
		return initAccount(response, accessToken.getToken(), accessToken.getSecret());
	}
	
	public String callUrl(OAuthService service, Token accessToken, String url){
		 OAuthRequest request = new OAuthRequest(Verb.GET, url);
		    service.signRequest(accessToken, request);
		Response response = request.send();
		if (response.getCode() != 200){
			logger.error("Error getting Facebook data for url: " + url);
			Set<String> headers = response.getHeaders().keySet();
			for (String name : headers) {
				logger.error(response.getHeader(name));
			}
		}
		return response.getBody();
	}
	
	private FacebookAccount initAccount(String response, String token, String secret) throws Exception{
		FacebookAccount account =new FacebookAccount();
		account.setToken(token);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		account.setAccountUniqueId(root.get("id").getValueAsText());
		account.setUserName(root.get("name").getValueAsText());
		account.setUserpicUrl("http://graph.facebook.com/" + root.get("username").getValueAsText() + "/picture");
		return account;
	}

	public String callUrl(String token, String url) {
		return callUrl(getService(), new Token(token, getMySocioSecret()), url);
	}
}
