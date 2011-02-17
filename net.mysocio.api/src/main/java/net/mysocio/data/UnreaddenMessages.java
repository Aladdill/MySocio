/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Entity
public class UnreaddenMessages extends SocioObject{
	private Long lastId;
	@ManyToMany(targetEntity = GeneralMessage.class, fetch = FetchType.EAGER)
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
