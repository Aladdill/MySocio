/**
 * 
 */
package net.mysocio.authentication.facebook;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.PersistenceAware;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.accounts.facebook.FacebookFriend;
import net.mysocio.data.accounts.facebook.FacebookFriendList;
import net.mysocio.data.management.DataManagerFactory;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;
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
@PersistenceAware
public class FacebookAuthenticationManager extends AbstractOauth2Manager {
	private static final Logger logger = LoggerFactory
			.getLogger(FacebookAuthenticationManager.class);

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
	protected Account getAccount(Token accessToken) throws Exception {
		FacebookAccount account = initAccount(accessToken.getToken());
		return account;
	}

	private FacebookAccount initAccount(String token) throws Exception {
		logger.debug("starting authentication");
		String response = callUrl(token, "https://graph.facebook.com/me");
		logger.debug("user data taken");
		FacebookAccount account;
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		String id = root.get("id").getValueAsText();
		account = (FacebookAccount) DataManagerFactory.getDataManager().getAccount(id);
		if (account != null) {
			logger.debug("Account found.");
			return account;
		}
		account = new FacebookAccount();
		account.setToken(token);
		account.setAccountUniqueId(id);
		account.setUserName(root.get("name").getValueAsText());
		account.setUserpicUrl("http://graph.facebook.com/" + id + "/picture");
		account.setEmail(root.get("email").getValueAsText());
		logger.debug("getting friends lists");
		response = callUrl(token, "https://graph.facebook.com/me/friendlists");
		logger.debug("friends lists taken");
		logger.debug("parsing friends list");
		account.setFriendLists(parseFriendLists(response));
		logger.debug("friends list parsed");
		logger.debug("getting friends");
		response = callUrl(token, "https://graph.facebook.com/me/friends");
		logger.debug("friends taken");
		logger.debug("parsing friends");
		account.setFriends(parseFriends(response));
		logger.debug("friends parsed");
		return account;
	}

	private List<FacebookFriend> parseFriends(String response) throws Exception {
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		JsonNode entry = root.get("data");
		List<FacebookFriend> friends = new ArrayList<FacebookFriend>();
		Iterator<JsonNode> elements = entry.getElements();
		while (elements.hasNext()) {
			JsonNode element = elements.next();
			FacebookFriend friend = new FacebookFriend();
			friend.setFacebookId(element.get("id").getValueAsText());
			friend.setName(element.get("name").getValueAsText());
			friends.add(friend);
		}
		return friends;
	}

	private List<FacebookFriendList> parseFriendLists(String response)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		JsonNode entry = root.get("data");
		List<FacebookFriendList> friendsLists = new ArrayList<FacebookFriendList>();
		Iterator<JsonNode> elements = entry.getElements();
		while (elements.hasNext()) {
			JsonNode element = elements.next();
			FacebookFriendList friendList = new FacebookFriendList();
			friendList.setFacebookId(element.get("id").getValueAsText());
			friendList.setName(element.get("name").getValueAsText());
			friendsLists.add(friendList);
		}
		return friendsLists;
	}

	public String callUrl(String token, String url) throws ConnectException {
		if (url.indexOf("?") > 0) {
			url += "&";
		} else {
			url += "?";
		}
		OAuthRequest request = new OAuthRequest(Verb.GET, url + "access_token="
				+ token);
		Response response = request.send();
		if (response.getCode() != 200) {
			logger.error("Error getting Facebook data for url: " + url);
			Set<String> headers = response.getHeaders().keySet();
			for (String name : headers) {
				logger.error(response.getHeader(name));
			}
			throw new ConnectException("Error getting Facebook data for url: "
					+ url);
		}
		return response.getBody();
	}
}
