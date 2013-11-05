/**
 * 
 */
package net.mysocio.connection.vkontakte;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import net.mysocio.authentication.vkontakte.VkontakteAuthenticationManager;
import net.mysocio.data.accounts.vkontakte.VkontakteAccount;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.contacts.vkontakte.VkontakteContact;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.camel.UserMessageProcessor;
import net.mysocio.data.messages.vkontakte.VkontakteMessage;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
public class VkontakteInputProcessor extends UserMessageProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914368846859623850L;
	@Transient
	private static final Logger logger = LoggerFactory
			.getLogger(VkontakteInputProcessor.class);
	private static final long MONTH = 30*24*3600l;
	private Long lastUpdate = 0l;
	private String url = "https://api.vkontakte.ru/method/wall.get?owner_id=vkontakte_id&filter=owner";
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
	private VkontakteMessage parseVkontakteMessage(String post) {
		VkontakteMessage message = new VkontakteMessage();
		return message;
	}
	
	public void process() throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Got trying to get messages for vkontakte account: " + accountId);
		}
		long to = System.currentTimeMillis();
//		VkontakteAuthenticationManager manager = new VkontakteAuthenticationManager();
//		VkontakteAccount account = DataManagerFactory.getDataManager().getObject(VkontakteAccount.class, getAccountId());
//		List<Contact> contacts = account.getContacts();
//		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
//		for (Contact contact : contacts) {
//			VkontakteContact vkcontact = (VkontakteContact)contact;
//			Response response = manager.getOauthResponse(token, url.replace("vkontakte_id", vkcontact.getVkontakteId()));
//			try {
//				JsonNode root = mapper.readTree(response.getBody());
//				System.out.println(root.getElements().next());
//				Iterator<JsonNode> elements = root.get("response").getElements();
//				while(elements.hasNext()){
//					JsonNode next = elements.next();
//				}
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//		}
		long from = lastUpdate;
		from = to - MONTH;
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
		VkontakteInputProcessor other = (VkontakteInputProcessor) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
