/**
 * 
 */
package net.mysocio.connection.facebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.messages.IMessage;
import net.mysocio.data.messages.facebook.FacebookMessage;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Aladdin
 * 
 */
public class FacebookSourceManager implements ISourceManager {
	private static final FacebookSourceManager instance = new FacebookSourceManager();
	private static final long MONTH = 30*24*3600*1000;

	/**
	 * 
	 */
	private FacebookSourceManager() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.mysocio.connection.readers.ISourceManager#getLastMessages(net.mysocio
	 * .connection.readers.ISource, java.lang.Long, java.lang.Long)
	 */
	public List<IMessage> getLastMessages(ISource source, Long from, Long to) throws Exception {
		FacebookSource fs = (FacebookSource) source;
		FacebookAccount fa = fs.getAccount();
		String url = "https://graph.facebook.com/me/home?format=json&until="+to;
		if (from == 0 || (to - from) > MONTH){
			from = to - MONTH;
		}
		url += "&since="+ from;
		String response = new FacebookAuthenticationManager().callUrl(fa.getToken(), url);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		JsonNode root = mapper.readTree(response);
		JsonNode entry = root.get("data");
		List<IMessage> messages = new ArrayList<IMessage>();
		Iterator<JsonNode> elements = entry.getElements();
		while (elements.hasNext()) {
			JsonNode element = elements.next();
			messages.add(parseFacebookMessage(fs, element));
		}
		return messages;
	}

	/**
	 * @param fs 
	 * @param element
	 * @throws ParseException
	 */
	private FacebookMessage parseFacebookMessage(FacebookSource fs, JsonNode element) throws ParseException {
		FacebookMessage message = new FacebookMessage();
		message.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(getAttribute(element, "created_time")).getTime());
		String text = getAttribute(element, "message");
		String type = getAttribute(element, "type");
		if (type.equals("link")){
			if(element.get("picture") != null){
				text += "</br><img alt=\"" + getAttribute(element, "caption") +"\" src=\"" + getAttribute(element, "picture") + "\">";
			}
			text +=	"</br><a href=\"" + getAttribute(element, "link") +" target=\"_blank\">" + getAttribute(element, "name") + "</a>";
		}else if(type.equals("photo") || type.equals("video")){
			text = "</br><img alt=\"" + getAttribute(element, "caption") +"\" src=\"" + getAttribute(element, "picture") + "\">" +
			"</br><a href=\"" + getAttribute(element, "link") +" target=\"_blank\">" + getAttribute(element, "name") + "</a>";
		}
		message.setText(text);
		message.setSourceId(fs.getId());
		String title = getAttribute(element, "story");
		if (title.length() <= 0){
			title = getAttribute(element.get("from"), "name");
		}
		message.setTitle(title);
		message.setUniqueId(getAttribute(element, "id"));
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

	public static FacebookSourceManager getInstance() {
		return instance;
	}
}
