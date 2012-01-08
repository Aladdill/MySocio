/**
 * 
 */
package net.mysocio.connection.facebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.facebook.FacebookMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Aladdin
 *
 */
public class FacebookInputProcessor implements Processor {
	private static final long MONTH = 30*24*3600l;
	private Long lastUpdate = 0l;
	private String token;
	private String username;
	private String accountId;
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
		message.setUserPic("https://graph.facebook.com/" + getAttribute(element.get("from"), "id") + "/picture");
		message.setTitle(title);
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
		String url = "https://graph.facebook.com/me/home?format=json&until="+toInSec;
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
			MessagesManager.getInstance().storeMessage(message);
			SocioTag tag = new SocioTag();
			tag.setValue("facebook.tag");
			tag.setUniqueId("facebook.tag");
			tag.setIconType("facebook.icon.general");
			message.addTag(tag);
			SocioTag tag1 = new SocioTag();
			tag1.setValue(username);
			tag1.setUniqueId(accountId);
			tag1.setIconType("facebook.icon.general");
			message.addTag(tag1);
			producerTemplate.sendBody(to,message);
		}
		lastUpdate = toInSec*1000;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
