/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class UnreaddenMessages extends SocioObject{
	@Persistent(nullValue=NullValue.DEFAULT)
	private Long lastId;
	@Join
	@Persistent(types={GeneralMessage.class},mappedBy = "id")
	private List<IMessage> messages = new ArrayList<IMessage>();
	/**
	 * @return the lastId
	 */
	public Long getLastId() {
		return lastId;
	}
	/**
	 * @param lastId the lastId to set
	 */
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
	/**
	 * @return the messages
	 */
	public List<IMessage> getMessages() {
		return messages;
	}
	public void addMessages(List<? extends IMessage> messages) {
		this.messages.addAll(messages);
	}
	public void addMessage(IMessage message) {
		this.messages.add(message);
	}
}
