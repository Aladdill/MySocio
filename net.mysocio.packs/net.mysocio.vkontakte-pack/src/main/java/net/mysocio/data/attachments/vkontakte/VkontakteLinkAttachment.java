package net.mysocio.data.attachments.vkontakte;

import com.google.code.morphia.annotations.Entity;

@Entity
public class VkontakteLinkAttachment extends VkontakteAttachment{
	private String url;
	private String title;
	private String description;
	private String imageSrc;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	@Override
	public String replacePlaceholders(String template) {
		String message = template.replace("message.picture", getImageSrc());
		message = message.replace("message.description", getDescription());
		message = message.replace("message.text", getTitle());
		message = message.replace("message.link", getUrl());
		return message;
	}
}
