/**
 * 
 */
package net.mysocio.data.messages;

import net.mysocio.data.UserObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Oslocomp
 *
 */
@Entity("readden_messages")
public class ReaddenMessage extends UserObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7131690601406608672L;
	private String messageUniqueId;
	public String getMessageUniqueId() {
		return messageUniqueId;
	}
	public void setMessageUniqueId(String messageUniqueId) {
		this.messageUniqueId = messageUniqueId;
	}
}
