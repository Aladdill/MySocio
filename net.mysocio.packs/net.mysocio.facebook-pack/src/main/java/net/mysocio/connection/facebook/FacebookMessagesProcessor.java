/**
 * 
 */
package net.mysocio.connection.facebook;

import java.net.URL;
import java.text.ParseException;
import java.util.List;

import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.management.camel.UserMessageProcessor;
import net.mysocio.data.management.exceptions.DuplicateMySocioObjectException;
import net.mysocio.data.messages.facebook.FacebookMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiCheckinMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiLinkMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiPhotoMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiStatusMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiVideoMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Transient;

import facebook4j.Application;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Post.Action;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

/**
 * @author Aladdin
 *
 */
public class FacebookMessagesProcessor extends UserMessageProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914368846859623850L;
	@Transient
	private static final Logger logger = LoggerFactory
			.getLogger(FacebookMessagesProcessor.class);
	private static final long MONTH = 30*24*3600l;
	private Long lastUpdate = 0l;
	private String token;
	private String accountId;
	@Transient
	private Facebook facebook;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param fs 
	 * @param element
	 * @throws ParseException
	 */
	private FacebookMessage parseFacebookMessage(Post post) throws ParseException {
		FacebookMessage message = new FacebookMessage();
		message.setFbId(post.getId());
		message.setDate(post.getCreatedTime().getTime());
		String type = post.getType();
		message.setType(type);
		URL picture = post.getPicture();
		if (picture != null){
			message.setPicture(picture.toString());
		}
		List<Action> actions = post.getActions();
		if (actions != null && !actions.isEmpty()){
			//here we suppose what every actions array has "Comments" as first object and it has "link" field 
			message.setLinkToMessage(actions.get(0).getLink());
		}else{
			message.setLinkToMessage("https://www.facebook.com/" + post.getId());
		}
		message.setCaption(post.getCaption());
		message.setText(post.getMessage());
		Application application = post.getApplication();
		if (application != null){
			message.setApplication(application.getName());
		}
		message.setDescription(post.getDescription());
		String title = post.getFrom().getName();
		String userId = post.getFrom().getId();
		message.setUserPic("https://graph.facebook.com/" + userId + "/picture");
		message.setTitle(title);
		message.setUserId(userId);
		message.setStory(post.getStory());
		
		URL link = post.getLink();
		if (link != null){
			message.setLink(link.toString());
		}else{
			message.setLink("https://www.facebook.com/" + userId);
		}
		message.setName(post.getName());
		URL source = post.getSource();
		if (source != null){
			message.setSource(source.toString());
		}
//		List<Property> properties = post.getProperties();
//		message.setProperties(properties);
//		message.setPrivacy(post.getPrivacy());
//		PagableList<IdNameEntity> likes = post.getLikes();
//		message.setLikes(likes);
//		Place place = post.getPlace();
//		message.setPlace(place);
		if (type.equals("photo")){
			message.setUiObjectName(FacebookUiPhotoMessage.NAME);
		}else if (type.equals("video")){
			message.setUiObjectName(FacebookUiVideoMessage.NAME);
		}else if (type.equals("link")){
			message.setUiObjectName(FacebookUiLinkMessage.NAME);
		}else if (type.equals("checkin")){
			message.setUiObjectName(FacebookUiCheckinMessage.NAME);
		}else if (type.equals("status")){
			message.setUiObjectName(FacebookUiStatusMessage.NAME);
		}
		return message;
	}
	
	public void process() throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Got trying to get messages for facebook account: " + accountId);
		}
		long to = System.currentTimeMillis();
		long from = lastUpdate;
		if (facebook == null){
			facebook = new FacebookFactory().getInstance();
			facebook.setOAuthAccessToken(new AccessToken(token, null));
		}
		if (from == 0 || (to - from) > MONTH){
			from = to - MONTH;
		}
//		Date fromDate = new Date(from);
//		logger.debug("Trying get FB messages from " + fromDate);
		ResponseList<Post> home = null;
		try {
			home = facebook.getHome(/*new Reading().since(fromDate)*/);
		} catch (FacebookException e) {
			logger.warn("Can't connect to facebook ", e);
			if (getAccountId()!= null){
				FacebookAccount account = DataManagerFactory.getDataManager().getObject(FacebookAccount.class, getAccountId());
				token = account.getToken();
				facebook = new FacebookFactory().getInstance();
				facebook.setOAuthAccessToken(new AccessToken(token, null));
				home = facebook.getHome();
			}
		}
		for (Post post : home) {
			FacebookMessage message = parseFacebookMessage(post);
			logger.debug("Got facebook message from user " + message.getTitle() + " with id " + message.getFbId());
			try {
				MessagesManager.getInstance().storeMessage(message);
			} catch (DuplicateMySocioObjectException e) {
				//if it's duplicate message - we ignore it
				logger.debug("Got duplicate Facebook message.",e);
				return;
			}
			addMessageForTag(message, message.getUserId());
		}
		lastUpdate = to;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacebookMessagesProcessor other = (FacebookMessagesProcessor) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
