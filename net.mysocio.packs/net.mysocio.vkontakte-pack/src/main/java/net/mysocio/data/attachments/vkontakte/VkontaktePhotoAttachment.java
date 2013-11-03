package net.mysocio.data.attachments.vkontakte;

import com.google.code.morphia.annotations.Entity;

@Entity
public class VkontaktePhotoAttachment extends VkontakteAttachment{
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
}
