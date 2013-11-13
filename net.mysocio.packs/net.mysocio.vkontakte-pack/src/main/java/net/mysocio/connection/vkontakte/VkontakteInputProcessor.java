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
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiMessage;
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
			attachmentNode = attachmentNode.get(type);
			if (type.equals("photo") || type.equals("posted_photo") || type.equals("graffiti")){
				attachment = new VkontaktePhotoAttachment();
				JsonNode text = attachmentNode.get("text");
				JsonNode src = attachmentNode.get("src");
				JsonNode srcBig = attachmentNode.get("src_big");
				message.setUiObjectName(VkontakteUiPhotoMessage.NAME);
				if (src != null){
					((VkontaktePhotoAttachment)attachment).setSrc(src.getTextValue());
				}
				if (srcBig != null){
					((VkontaktePhotoAttachment)attachment).setSrcBig(srcBig.getTextValue());
				}
				if (text != null){
					((VkontaktePhotoAttachment)attachment).setText(text.getTextValue());
				}
			} else {
				JsonNode title = attachmentNode.get("title");
				JsonNode description = attachmentNode.get("description");
				if (type.equals("link") || type.equals("note")){
					attachment = new VkontakteLinkAttachment();
					JsonNode imageSrc = attachmentNode.get("image_src");
					JsonNode urlNode = attachmentNode.get("url");
					message.setUiObjectName(VkontakteUiLinkMessage.NAME);
					if (urlNode != null){
						((VkontakteLinkAttachment)attachment).setUrl(urlNode.getTextValue());
					}
					if (title != null){
						((VkontakteLinkAttachment)attachment).setTitle(title.getTextValue());
					}
					if (description != null){
						((VkontakteLinkAttachment)attachment).setDescription(description.getTextValue());
					}
					if (imageSrc != null){
						((VkontakteLinkAttachment)attachment).setImageSrc(imageSrc.getTextValue());
					}
				}else if (type.equals("video")){
					attachment = new VkontakteVideoAttachment();
					JsonNode image = attachmentNode.get("image");
					JsonNode imageBig = attachmentNode.get("image_big");
					message.setUiObjectName(VkontakteUiVideoMessage.NAME);
					if (image != null){
						((VkontakteVideoAttachment)attachment).setSrc(image.getTextValue());
					}
					if (imageBig != null){
						((VkontakteVideoAttachment)attachment).setSrcBig(imageBig.getTextValue());
					}
					if (title != null){
						((VkontakteVideoAttachment)attachment).setTitle(title.getTextValue());
					}
					if (description != null){
						((VkontakteVideoAttachment)attachment).setDescription(description.getTextValue());
					}
				}else{
					message.setUiObjectName(VkontakteUiMessage.NAME);
				}
			}
			message.setAttachment(attachment);
		}
		message.setVkId(messageJson.get("from_id") + "_" + messageJson.get("id"));
		message.setDate(messageJson.get("date").getLongValue()*1000);
		message.setText(messageJson.get("text").getTextValue());
		return message;
	}
	
	public void process() throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Got trying to get messages for vkontakte account: " + accountId);
		}
		long to = System.currentTimeMillis();
		long from = lastUpdate;
		
		if (from == 0 || (to - from) > MONTH){
			from = to - MONTH;
		}
		VkontakteAuthenticationManager manager = new VkontakteAuthenticationManager();
		VkontakteAccount account = DataManagerFactory.getDataManager().getObject(VkontakteAccount.class, getAccountId());
		List<Contact> contacts = account.getContacts();
		logger.debug("Got list of contacts of size: " + contacts.size());
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		for (Contact contact : contacts) {
			VkontakteContact vkcontact = (VkontakteContact)contact;
			Response response = manager.getOauthResponse(token, url.replace("vkontakte_id", vkcontact.getVkontakteId()));
			logger.debug("Got response for contact with id " + vkcontact.getVkontakteId() + " with length: " + response.getBody().length());
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
					message.setTitle(contact.getName());
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
