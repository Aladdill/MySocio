/**
 * 
 */
package net.mysocio.data.messages.vkontakte;

import net.mysocio.data.attachments.vkontakte.VkontakteAttachment;
import net.mysocio.data.messages.UserMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiMessage;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("messages")
public class VkontakteMessage extends UserMessage {
	private String vkId = "";
	@Embedded
	private VkontakteAttachment attachment;
	private String uiObjectName = VkontakteUiMessage.NAME;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5110172305702339723L;
	
	@Override
	public String replacePlaceholders(String template) {
		String message = super.replacePlaceholders(template);
		message = message.replace("message.outer.link", getLink());
		if (attachment != null){
			message = attachment.replacePlaceholders(message);
		}
		return message;
	}

	@Override
	public Object getUniqueFieldValue() {
		return vkId;
	}

	@Override
	public String getUniqueFieldName() {
		return "vkId";
	}

	@Override
	public String getNetworkIcon() {
		return "images/networksIcons/v-kontakte.png";
	}

	@Override
	public String getReadenNetworkIcon() {
		return "images/networksIcons/v-kontakte-gray.png";
	}

	@Override
	public String getLink() {
		return "http://vk.com/wall" + vkId;
	}

	@Override
	public String getUiName() {
		return uiObjectName;
	}
	
	@Override
	public String getUiCategory() {
		return VkontakteUiMessage.CATEGORY;
	}
	
	public String getVkId() {
		return vkId;
	}


	public void setVkId(String vkId) {
		this.vkId = vkId;
	}

	public String getUiObjectName() {
		return uiObjectName;
	}


	public void setUiObjectName(String uiObjectName) {
		this.uiObjectName = uiObjectName;
	}

	public VkontakteAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(VkontakteAttachment attachment) {
		this.attachment = attachment;
	}
}
