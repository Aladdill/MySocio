package net.mysocio.data.attachments.vkontakte;

import net.mysocio.data.SocioObject;

import com.google.code.morphia.annotations.Entity;

@Entity
public abstract class VkontakteAttachment extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1545539182375336387L;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	abstract public String replacePlaceholders(String template);
}
