/**
 * 
 */
package net.mysocio.authentication.vkontakte;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.data.ContactsList;
import net.mysocio.data.IDataManager;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.vkontakte.VkontakteAccount;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.contacts.vkontakte.VkontakteContact;
import net.mysocio.data.management.DataManagerFactory;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.VkontakteApi;
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
public class VkontakteAuthenticationManager extends AbstractOauth2Manager {
	private static final Logger logger = LoggerFactory
			.getLogger(VkontakteAuthenticationManager.class);

	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUserIdentifier() {
		return "vkontakte";
	}

	@Override
	protected Class<? extends Api> getApiClass() {
		return VkontakteApi.class;
	}
	
	public Response getOauthResponse(String token, String url) throws Exception {
		return getOauthedResponse(new Token(token, getMySocioSecret()), url, getService());
	}

	@Override
	protected Account getAccount(Token accessToken) throws Exception {
		String url = "https://api.vkontakte.ru/method/users.get?fields=photo_50,screen_name";
	    OAuthService service = getService();
	    Response response = getOauthedResponse(accessToken, url, service);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode user = root.get("response").getElements().next();
		String uniqueId = user.get("uid").getValueAsText();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		checkUserInvitation(uniqueId, dataManager);
		VkontakteAccount account = (VkontakteAccount) dataManager.getAccount(uniqueId);
		String token = accessToken.getToken();
		if (account != null) {
			logger.debug("Account found.");
			account.setToken(token);
			dataManager.saveObject(account);
			return account;
		}
		account = new VkontakteAccount();
		account.setToken(token);
		account.setAccountUniqueId(uniqueId);
		account.setUserName(user.get("first_name").getValueAsText() + " " + user.get("last_name").getValueAsText());
		account.setUserpicUrl(user.get("photo_50").getValueAsText());
		//vkontakte friends lists are open only for desktop apps :(
		account.setContactsLists(Collections.<ContactsList>emptyList());
		logger.debug("parsing friends");
		List<Contact> friends = parseFriends(service, accessToken);
		dataManager.saveObjects(Contact.class, friends);
		account.setContacts(friends);
		logger.debug("friends parsed");
		dataManager.saveObject(account);
		return account;
	}

	private Response getOauthedResponse(Token accessToken, String url,
			OAuthService service) throws ConnectException {
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		service.signRequest(accessToken, request);
	    Response response = request.send();
		if (response.getCode() != 200) {
			logger.error("Error getting Vkontakte data for url: " + url + " token: " + accessToken.getToken());
			Set<String> headers = response.getHeaders().keySet();
			for (String name : headers) {
				logger.error(response.getHeader(name));
			}
			throw new ConnectException("Error getting Google data for url: "
					+ url);
		}
		return response;
	}

	private List<Contact> parseFriends(OAuthService service, Token accessToken) throws Exception {
		Response response = getOauthedResponse(accessToken, "https://api.vkontakte.ru/method/friends.get?fields=first_name,last_name,photo_50", service);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response.getBody());
		Iterator<JsonNode> friends = root.get("response").getElements();
		List<Contact> contacts = new ArrayList<Contact>();
		while (friends.hasNext()){
			JsonNode next = friends.next();
			VkontakteContact friend = new VkontakteContact();
			friend.setUserpicUrl(next.get("photo_50").getValueAsText());
			friend.setVkontakteId(next.get("uid").getValueAsText());
			friend.setName(next.get("first_name").getValueAsText() + " " + next.get("last_name").getValueAsText());
			contacts.add(friend);
		}
		return contacts;
	}
}
