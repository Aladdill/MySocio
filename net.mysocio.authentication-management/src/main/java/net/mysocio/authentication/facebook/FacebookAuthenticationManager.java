/**
 * 
 */
package net.mysocio.authentication.facebook;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.authentication.UserIdentifier;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.FacebookAccount;

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
	protected UserIdentifier getUserIdentifier() {
		return UserIdentifier.facebook;
	}


	@Override
	protected Class<? extends Api> getApiClass() {
		return FacebookApi.class;
	}

	@Override
	protected Account getAccount(OAuthService service, Token accessToken)
			throws Exception {
		 OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
		    service.signRequest(accessToken, request);
		Response response = request.send();
		FacebookAccount account = new FacebookAccount();
		account.setToken(accessToken.getToken());
		if (response.getCode() != 200){
			logger.error("Error getting Facebook account data.");
			Set<String> headers = response.getHeaders().keySet();
			for (String name : headers) {
				logger.error(response.getHeader(name));
			}
		}
		initAccount(response.getBody(), account);
		return account;
	}
	
	private void initAccount(String response, FacebookAccount account) throws Exception{
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		Iterator<Entry<String, JsonNode>> fields = root.getFields();
		while(fields.hasNext()){
			Entry<String, JsonNode> entry = fields.next();
			if ("id".equals(entry.getKey())){
				account.setAccountUniqueId(entry.getValue().getValueAsText());
			}else if ("name".equals(entry.getKey())){
				account.setUserName(entry.getValue().getValueAsText());
			}
		}
	}
}
