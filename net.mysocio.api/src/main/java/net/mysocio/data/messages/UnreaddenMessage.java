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
	
	public String replacePlaceholders(String template) {
		String message = template.replace("message.title", this.message.getTitle());
		message = message.replace("message.id", getId().toString());
		message = message.replace("message.text", this.message.getText());
		message = message.replace("message.link", this.message.getLink());
		if (this.message instanceof UserMessage){
			message = message.replace("network.icon.readen", ((UserMessage)this.message).getReadenNetworkIcon());
			message = message.replace("network.icon", ((UserMessage)this.message).getNetworkIcon());
			message = message.replace("user.pic", ((UserMessage)this.message).getUserPic());
		}
		return message;
	}
}
