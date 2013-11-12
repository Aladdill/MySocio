/**
 * 
 */
package net.mysocio.connection.vkontakte;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import net.mysocio.authentication.vkontakte.VkontakteAuthenticationManager;
import net.mysocio.data.accounts.vkontakte.VkontakteAccount;
import net.mysocio.data.attachments.vkontakte.VkontakteAttachment;
import net.mysocio.data.attachments.vkontakte.VkontakteLinkAttachment;
import net.mysocio.data.attachments.vkontakte.VkontaktePhotoAttachment;
import net.mysocio.data.attachments.vkontakte.VkontakteVideoAttachment;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.contacts.vkontakte.VkontakteContact;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.management.camel.UserMessageProcessor;
import net.mysocio.data.management.exceptions.DuplicateMySocioObjectException;
import net.mysocio.data.messages.vkontakte.VkontakteMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiLinkMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiPhotoMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiVideoMessage;

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
	private VkontakteMessage parseVkontakteMessage(JsonNode messageJson) {
		VkontakteMessage message = new VkontakteMessage();
		messageJson.get("id");
		JsonNode attachmentNode = messageJson.get("attachment");
		if (attachmentNode != null){
			String type = attachmentNode.get("type").getTextValue();
			VkontakteAttachment attachment = null;
			if (type.equals("photo") || type.equals("posted_photo") || type.equals("graffiti")){
				attachment = new VkontaktePhotoAttachment();
				message.setUiObjectName(VkontakteUiPhotoMessage.NAME);
				((VkontaktePhotoAttachment)attachment).setSrc(attachmentNode.get("src").getTextValue());
				((VkontaktePhotoAttachment)attachment).setSrcBig(attachmentNode.get("src_big").getTextValue());
				((VkontaktePhotoAttachment)attachment).setText(attachmentNode.get("text").getTextValue());
			}else if (type.equals("link") || type.equals("note")){
				attachment = new VkontakteLinkAttachment();
				message.setUiObjectName(VkontakteUiLinkMessage.NAME);
				((VkontakteLinkAttachment)attachment).setUrl(attachmentNode.get("url").getTextValue());
				((VkontakteLinkAttachment)attachment).setTitle(attachmentNode.get("title").getTextValue());
				((VkontakteLinkAttachment)attachment).setDescription(attachmentNode.get("description").getTextValue());
				((VkontakteLinkAttachment)attachment).setImageSrc(attachmentNode.get("image_src").getTextValue());
			}else if (type.equals("video")){
				attachment = new VkontakteVideoAttachment();
				message.setUiObjectName(VkontakteUiVideoMessage.NAME);
				((VkontakteVideoAttachment)attachment).setSrc(attachmentNode.get("src").getTextValue());
				((VkontakteVideoAttachment)attachment).setSrcBig(attachmentNode.get("src_big").getTextValue());
				((VkontakteVideoAttachment)attachment).setTitle(attachmentNode.get("title").getTextValue());
				((VkontakteVideoAttachment)attachment).setDescription(attachmentNode.get("description").getTextValue());
			}
			message.setAttachment(attachment);
		}
		message.setVkId(messageJson.get("from_id").getTextValue() + "_" + messageJson.get("id").getTextValue());
		message.setTitle(messageJson.get("text").getTextValue());
		message.setDate(messageJson.get("date").getLongValue());
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
		
		if (from == 0 || (to - from) > MONTH){
			from = to - MONTH;
		}
		VkontakteAuthenticationManager manager = new VkontakteAuthenticationManager();
		VkontakteAccount account = DataManagerFactory.getDataManager().getObject(VkontakteAccount.class, getAccountId());
		List<Contact> contacts = account.getContacts();
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		for (Contact contact : contacts) {
			VkontakteContact vkcontact = (VkontakteContact)contact;
			Response response = manager.getOauthResponse(token, url.replace("vkontakte_id", vkcontact.getVkontakteId()));
			try {
				JsonNode root = mapper.readTree(response.getBody());
				Iterator<JsonNode> elements = root.get("response").getElements();
				if (elements.hasNext()){
					JsonNode count = elements.next();
					if (count == null){
						logger.debug("This strange count is null");
					}
				}
				while(elements.hasNext()){
					VkontakteMessage message = parseVkontakteMessage(elements.next());
					logger.debug("Got vkontakte message from user " + message.getTitle() + " with id " + message.getVkId());
					message.setUserPic(contact.getUserpicUrl());
					message.setUserId((String)contact.getUniqueFieldValue());
					try {
						MessagesManager.getInstance().storeMessage(message);
					} catch (DuplicateMySocioObjectException e) {
						//if it's duplicate message - we ignore it
						logger.debug("Got duplicate Vkontakte message.",e);
					}
					addMessageForTag(message, VkontakteMessage.class, message.getUserId());
				}
			} catch (JsonProcessingException e) {
				logger.error("Failed to parse vkontakte messages for user " + contact.getName());
			}
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
		VkontakteInputProcessor other = (VkontakteInputProcessor) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
