/**
 * 
 */
package net.mysocio.data.messages;

import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Oslocomp
 *
 */
@Entity("ureadden_messages")
public class UnreaddenMessage extends SocioObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2540562659525473668L;
	private String userId;
	private String messageId;
	private SocioTag tag;
	private long messageDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
}
