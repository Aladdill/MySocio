/**
 * 
 */
package net.mysocio.data.messages;

import net.mysocio.data.UserObject;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;
import com.google.code.morphia.annotations.Reference;

/**
 * @author Oslocomp
 *
 */
@Entity("unreadden_messages")
@Indexes(@Index("userId, tagId"))
public class UnreaddenMessage extends UserObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2540562659525473668L;
	@Reference
	private GeneralMessage message;
	private String tagId;
	private long messageDate;
	
	public long getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(long messageDate) {
		this.messageDate = messageDate;
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
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
