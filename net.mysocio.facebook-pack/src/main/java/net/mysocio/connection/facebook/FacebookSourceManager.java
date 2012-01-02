/**
 * 
 */
package net.mysocio.connection.facebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.connection.readers.IAccountSourceManager;
import net.mysocio.connection.readers.ISource;
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
public class FacebookSourceManager implements IAccountSourceManager {
	private static final FacebookSourceManager instance = new FacebookSourceManager();
	private static final long MONTH = 30*24*3600l;

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
		FacebookAccount fa = (FacebookAccount)fs.getAccount();
		long toInSec = to/1000;
		long fromInSec = from/1000;
		String url = "https://graph.facebook.com/me/home?format=json&until="+toInSec;
		if (fromInSec == 0 || (to - from) > MONTH){
			fromInSec = toInSec - MONTH;
		}
		url += "&since="+ fromInSec;
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

	public static FacebookSourceManager getInstance() {
		return instance;
	}

	public Map<String, List<String>> orderMessagesByContactsTags(ISource source, List<IMessage> messages) {
		FacebookSource fs = (FacebookSource) source;
		FacebookAccount fa = (FacebookAccount)fs.getAccount();
		return Collections.emptyMap();
	}

	public List<IMessage> getFirstBulkOfMessages(ISource source)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
