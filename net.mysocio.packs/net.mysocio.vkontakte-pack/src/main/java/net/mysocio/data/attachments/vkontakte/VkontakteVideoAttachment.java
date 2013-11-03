package net.mysocio.data.attachments.vkontakte;

import com.google.code.morphia.annotations.Entity;

@Entity
public class VkontakteVideoAttachment extends VkontakteAttachment{
	private String src;
	private String srcBig;
	private String title;
	private String description;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
