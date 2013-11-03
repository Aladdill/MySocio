package net.mysocio.data.attachments.vkontakte;

import com.google.code.morphia.annotations.Entity;

@Entity
public abstract class VkontakteAttachment {
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
