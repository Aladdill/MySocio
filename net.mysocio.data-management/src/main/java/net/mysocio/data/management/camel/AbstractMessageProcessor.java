/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public abstract class AbstractMessageProcessor extends AbstractProcessor{
	public static final String ACTIVEMQ_READEN_MESSAGE = "activemq:readenMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2112778445393534126L;

	
	private String to;
	
	protected void addMessageForTag(GeneralMessage message, String tag) throws Exception {
		UnreaddenMessage unreaddenMessage = new UnreaddenMessage();
		unreaddenMessage.setMessageDate(message.getDate());
		unreaddenMessage.setMessage(message);
		unreaddenMessage.setTagId(tag);
		DataManagerFactory.getDataManager().sendPackageToRoute(to, unreaddenMessage);
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
