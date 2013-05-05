/**
 * 
 */
package net.mysocio.authentication.google;


import java.net.ConnectException;
import java.util.Set;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.google.GoogleAccount;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Aladdin
 *
 */
public class GoogleAuthenticationManager extends AbstractOauth2Manager{
	private static final Logger logger = LoggerFactory.getLogger(GoogleAuthenticationManager.class);
	
	@Override
	protected Logger getLogger(){
		return logger;
	}
	@Override
	protected String getUserIdentifier() {
		return "google";
	}
	@Override
	protected Class<? extends Api> getApiClass() {
		return GoogleOauth2Api.class;
	}
	
	protected Account getAccount(Token accessToken) throws Exception {
		String url = "https://www.googleapis.com/oauth2/v2/userinfo";
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		request.addHeader("Authorization", "OAuth " + accessToken);
		Response response = request.send();
		if (response.getCode() != 200) {
			logger.error("Error getting Facebook data for url: " + url);
			Set<String> headers = response.getHeaders().keySet();
			for (String name : headers) {
				logger.error(response.getHeader(name));
			}
			throw new ConnectException("Error getting Google data for url: "
					+ url);
		}
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response.getBody());
		String name = root.get("name").getValueAsText();
		logger.debug("got contacts with title" + name);
		GoogleAccount account = new GoogleAccount();
		account.setToken(accessToken.getToken());
		account.setRefreshToken(getRefreshToken(accessToken.getRawResponse()));
		account.setUserName(name);
		account.setAccountUniqueId(root.get("email").getValueAsText());
		account.setAccountUniqueId(root.get("id").getValueAsText());
		account.setUserpicUrl(root.get("picture").getValueAsText());
		return account;
	}
	
	private String getRefreshToken(String response) throws Exception{
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createJsonParser(response);
		String refreshToken = "";
		jp.nextToken();
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			if ("refresh_token".equals(jp.getCurrentName())){
				jp.nextToken();
				return jp.getText();
			}
		}
		return refreshToken;
	}
}
