/**
 * 
 */
package net.mysocio.authentication.facebook;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.data.ContactsList;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.accounts.facebook.FacebookFriendList;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.contacts.facebook.FacebookContact;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.UnapprovedUserException;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Friendlist;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;

/**
 * @author Aladdin
 * 
 */
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
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAccessToken(new AccessToken(token, null));
		logger.debug("user data taken");
		FacebookAccount account;
		User user = facebook.getMe();
		String email = user.getEmail();
		if (!isApprovedUser(email)){
			throw new UnapprovedUserException();
		}
		String id = user.getId();
		account = (FacebookAccount) DataManagerFactory.getDataManager().getAccount(id);
		if (account != null) {
			logger.debug("Account found.");
			return account;
		}
		account = new FacebookAccount();
		account.setToken(token);
		account.setAccountUniqueId(id);
		account.setUserName(user.getName());
		account.setUserpicUrl("http://graph.facebook.com/" + id + "/picture");
		account.setEmail(email);
		logger.debug("parsing friends list");
		account.setContactsLists(parseFriendLists(facebook));
		logger.debug("friends list parsed");
		logger.debug("parsing friends");
		List<Contact> friends = parseFriends(facebook);
		DataManagerFactory.getDataManager().saveObjects(Contact.class, friends);
		account.setContacts(friends);
		logger.debug("friends parsed");
		DataManagerFactory.getDataManager().saveObject(account);
		return account;
	}

	private boolean isApprovedUser(String email) {
		try {
			ResourceBundle approvedBundle = ResourceBundle.getBundle("approved");
			return approvedBundle.getString("approved.users").contains(email);
		} catch (Exception e) {
			logger.error("No authentication properties were found.",e);
		}
		return false;
	}

	private List<Contact> parseFriends(Facebook facebook) throws Exception {
		ResponseList<Friend> facebookFriends = facebook.getFriends();
		List<Contact> friends = new ArrayList<Contact>();
		for (Friend facebookFriend : facebookFriends) {
			FacebookContact friend = new FacebookContact();
			friend.setFacebookId(facebookFriend.getId());
			friend.setName(facebookFriend.getName());
			friends.add(friend);
		}
		return friends;
	}

	private List<ContactsList> parseFriendLists(Facebook facebook) throws Exception {
		ResponseList<Friendlist> friendlists = facebook.getFriendlists();
		List<ContactsList> friendsLists = new ArrayList<ContactsList>();
		for (Friendlist friendlist : friendlists) {
			FacebookFriendList friendList = new FacebookFriendList();
			friendList.setFacebookId(friendlist.getId());
			friendList.setName(friendlist.getName());
			try {
				DataManagerFactory.getDataManager().saveObject(friendList);
			} catch (Exception e) {
				// nothing to do, just ignore
			}
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
