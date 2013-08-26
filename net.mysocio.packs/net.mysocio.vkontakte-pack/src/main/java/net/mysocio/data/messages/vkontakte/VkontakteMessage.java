/**
 * 
 */
package net.mysocio.data.messages.vkontakte;

import net.mysocio.data.messages.UserMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("messages")
public class VkontakteMessage extends UserMessage {
	private String vkId = "";
	private String src = "";
	private String srcBig = "";
	private String duration = "";
	private String performer = "";
	private String size = "";
	private String ext = "";
	private String url = "";
	private String image_src = "";
	private String app_name = "";
	private String question = "";
	private String ncom = "";
	private String uiObjectName = VkontakteUiMessage.NAME;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5110172305702339723L;
	
	@Override
	public String replacePlaceholders(String template) {
		String message = super.replacePlaceholders(template);
		message = message.replace("message.link", getLink());
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


	public String getVkId() {
		return vkId;
	}


	public void setVkId(String vkId) {
		this.vkId = vkId;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrcBig() {
		return srcBig;
	}

	public void setSrcBig(String srcBig) {
		this.srcBig = srcBig;
	}

	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getPerformer() {
		return performer;
	}


	public void setPerformer(String performer) {
		this.performer = performer;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getExt() {
		return ext;
	}


	public void setExt(String ext) {
		this.ext = ext;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getImage_src() {
		return image_src;
	}


	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}


	public String getApp_name() {
		return app_name;
	}


	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getNcom() {
		return ncom;
	}


	public void setNcom(String ncom) {
		this.ncom = ncom;
	}


	public String getUiObjectName() {
		return uiObjectName;
	}


	public void setUiObjectName(String uiObjectName) {
		this.uiObjectName = uiObjectName;
	}
}
