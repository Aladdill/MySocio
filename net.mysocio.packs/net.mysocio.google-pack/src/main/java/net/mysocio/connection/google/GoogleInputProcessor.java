/**
 * 
 */
package net.mysocio.connection.google;

import java.text.ParseException;

import net.mysocio.data.management.camel.UserMessageProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
public class GoogleInputProcessor extends UserMessageProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914368846859623850L;
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(GoogleInputProcessor.class);
	private String token;
	private String accountId;

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
	/*private GoogleMessage parseGoogleMessage(Post post) throws ParseException {
		GoogleMessage message = new GoogleMessage();
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
	}*/
	
	public void process() throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Got trying to get messages for google account: " + accountId);
		}
//		long to = System.currentTimeMillis();
//		long from = getLastUpdate();
	/*	if (facebook == null){
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
			home = facebook.getHome(new Reading().since(fromDate));
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
			GoogleMessage message = parseGoogleMessage(post);
			logger.debug("Got facebook message from user " + message.getTitle() + " with id " + message.getFbId());
			try {
				MessagesManager.getInstance().storeMessage(message);
			} catch (DuplicateMySocioObjectException e) {
				//if it's duplicate message - we ignore it
				logger.debug("Got duplicate Facebook message.",e);
				return;
			}
			addMessageForTag(message, message.getUserId());
		}*/
//		setLastUpdate(to);
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
		GoogleInputProcessor other = (GoogleInputProcessor) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
