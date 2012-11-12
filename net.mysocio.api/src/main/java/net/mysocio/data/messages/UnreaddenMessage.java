/**
 * 
 */
package net.mysocio.data.messages;

import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author Oslocomp
 *
 */
@Entity("unreadden_messages")
public class UnreaddenMessage extends SocioObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2540562659525473668L;
	private String userId;
	@Reference
	private GeneralMessage message;
	@Embedded
	private SocioTag tag;
	private long messageDate;
	private String messageId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(long messageDate) {
		this.messageDate = messageDate;
	}
	public SocioTag getTag() {
		return tag;
	}
	public void setTag(SocioTag tag) {
		this.tag = tag;
	}
	/**
	 * @return the message
	 */
	public GeneralMessage getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(GeneralMessage message) {
		this.message = message;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
