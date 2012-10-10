/**
 * 
 */
package net.mysocio.connection.facebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.management.AbstractMessageProcessor;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.DuplicateMySocioObjectException;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.facebook.FacebookMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiCheckinMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiLinkMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiPhotoMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiStatusMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiVideoMessage;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class FacebookInputProcessor extends AbstractMessageProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(FacebookInputProcessor.class);
	private static final long MONTH = 30*24*3600l;
	private Long lastUpdate = 0l;
	private String token;

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
	private FacebookMessage parseFacebookMessage(JsonNode element) throws ParseException {
		FacebookMessage message = new FacebookMessage();
		message.setUniqueId(getAttribute(element, "id"));
		message.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(getAttribute(element, "created_time")).getTime());
		String type = getAttribute(element, "type");
		message.setType(type);
		message.setPicture(getAttribute(element, "picture"));
		JsonNode actions = element.get("actions");
		if (actions.has(0)){
			//here we suppose what every actions array has "Comments" as first object and it has "link" field 
			message.setLinkToMessage(getAttribute(actions.get(0), "link"));
		}
		message.setCaption(getAttribute(element, "caption"));
		message.setText(getAttribute(element, "message"));
		message.setApplication(getAttribute(element, "application"));
		message.setDescription(getAttribute(element, "description"));
		String title = getAttribute(element.get("from"), "name");
		String userId = getAttribute(element.get("from"), "id");
		message.setUserPic("https://graph.facebook.com/" + userId + "/picture");
		message.setTitle(title);
		message.setUserId(userId);
		message.setStory(getAttribute(element, "story"));
		message.setLink(getAttribute(element, "link"));
		message.setName(getAttribute(element, "name"));
		message.setSource(getAttribute(element, "source"));
		message.setProperties(getAttribute(element, "properties"));
		message.setPrivacy(getAttribute(element, "privacy"));
		message.setLikes(getAttribute(element, "likes"));
		message.setPlace(getAttribute(element, "place"));
		message.setPlace(getAttribute(element, "place"));
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
	
	/**
	 * @param element
	 * @param string
	 * @return
	 */
	public String getAttribute(JsonNode element, String string) {
		JsonNode node = element.get(string);
		String ret = "";
		if (node != null){
			ret = node.getTextValue();
		}
		return ret;
	}

	public void process(Exchange exchange) throws Exception {
		long toInSec = System.currentTimeMillis()/1000;
		long fromInSec = lastUpdate/1000;
		String url = "https://graph.facebook.com/me/home?format=json";
		if (fromInSec == 0 || (toInSec - fromInSec) > MONTH){
			fromInSec = toInSec - MONTH;
		}
		url += "&since="+ fromInSec;
		String response = new FacebookAuthenticationManager().callUrl(token, url);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		JsonNode entry = root.get("data");
		Iterator<JsonNode> elements = entry.getElements();
		ProducerTemplate producerTemplate = CamelContextManager.getProducerTemplate();
		while (elements.hasNext()) {
			JsonNode element = elements.next();
			FacebookMessage message = parseFacebookMessage(element);
			logger.debug("Got facebook message from user " + message.getTitle() + " with id " + message.getUniqueId());
			SocioTag tag = new SocioTag();
			tag.setValue(message.getTitle());
			tag.setIconType("facebook.icon.general");
			try {
				MessagesManager.getInstance().storeMessage(message);
			} catch (DuplicateMySocioObjectException e) {
				//if it's duplicate message - we ignore it
				return;
			}
			addMessageForTag(producerTemplate, message, tag);
			for (SocioTag sourceTag : tags) {
				addMessageForTag(producerTemplate, message, sourceTag);
			}
		}
		lastUpdate = toInSec*1000;
	}
}
