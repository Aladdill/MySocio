/**
 * 
 */
package net.mysocio.authentication.google;


import java.util.List;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.authentication.UserIdentifier;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.GoogleAccount;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gdata.data.ParseSource;
import com.google.gdata.data.Person;
import com.google.gdata.data.contacts.ContactFeed;

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
	protected UserIdentifier getUserIdentifier() {
		return UserIdentifier.google;
	}
	@Override
	protected Class<? extends Api> getApiClass() {
		return GoogleOauth2Api.class;
	}
	@Override
	protected Account getAccount(OAuthService service, Token accessToken) throws Exception {
		 OAuthRequest request = new OAuthRequest(Verb.GET, "https://www.google.com/m8/feeds/");
		    service.signRequest(accessToken, request);
		Response response = request.send();
		ContactFeed resultFeed = new ContactFeed(ContactFeed.readFeed(new ParseSource(response.getStream())));
		logger.debug("got contacts with title" + resultFeed.getTitle().getPlainText());
		List<Person> authors = resultFeed.getAuthors();
		GoogleAccount account = new GoogleAccount();
		account.setToken(accessToken.getToken());
		account.setRefreshToken(getRefreshToken(accessToken.getRawResponse()));
		for (Person person : authors) {
			account.setUserName(person.getName());
			account.setAccountUniqueId(person.getEmail());
		}
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
