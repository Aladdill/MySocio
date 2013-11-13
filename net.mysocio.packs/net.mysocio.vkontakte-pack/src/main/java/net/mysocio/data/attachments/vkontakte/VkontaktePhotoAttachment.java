package net.mysocio.data.attachments.vkontakte;

import com.google.code.morphia.annotations.Entity;

@Entity
public class VkontaktePhotoAttachment extends VkontakteAttachment{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String src;
	private String srcBig;
	private String text;
	
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String replacePlaceholders(String template) {
		String message = template;
		if (srcBig != null){
			message = message.replace("message.picture", getSrcBig());
		}
		if (text != null){
			message = message.replace("message.text", getText());
		}
		return message;
	}
}
