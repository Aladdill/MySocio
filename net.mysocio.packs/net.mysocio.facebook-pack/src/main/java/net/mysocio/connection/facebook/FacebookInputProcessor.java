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
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.UnreaddenMessage;
import net.mysocio.data.messages.facebook.FacebookMessage;

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
	private String to;
	

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

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
		String text = "";
		String type = getAttribute(element, "type");
		if (element.get("picture") != null){
			text = "<img alt=\"\" src=\"" + getAttribute(element, "picture") + "\">";
		}
		text +=	"</br><a href=\"" + getAttribute(element, "link") +"\" target=\"_blank\">" + getAttribute(element, "name") + "</a>";
		text += getAttribute(element, "message");
		if (type.equals("link")){
			text += "</br>" + getAttribute(element, "caption");
		}
		text += "</br>" + getAttribute(element, "description");
		text += "&nbsp;" + getAttribute(element, "story");
		message.setText(text);
		String title = getAttribute(element.get("from"), "name");
		String userId = getAttribute(element.get("from"), "id");
		message.setUserPic("https://graph.facebook.com/" + userId + "/picture");
		message.setTitle(title);
		message.setUserId(userId);
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
			MessagesManager.getInstance().storeMessage(message);
			UnreaddenMessage unreaddenMessage = new UnreaddenMessage();
			unreaddenMessage.setMessageDate(message.getDate());
			unreaddenMessage.setMessageId(message.getId().toString());
			unreaddenMessage.setTag(tag);
			producerTemplate.sendBody(to,unreaddenMessage);
			for (SocioTag sourceTag : tags) {
				unreaddenMessage = new UnreaddenMessage();
				unreaddenMessage.setMessageDate(message.getDate());
				unreaddenMessage.setMessageId(message.getId().toString());
				unreaddenMessage.setTag(sourceTag);
    			producerTemplate.sendBody(to,unreaddenMessage);
			}
		}
		lastUpdate = toInSec*1000;
	}
}
