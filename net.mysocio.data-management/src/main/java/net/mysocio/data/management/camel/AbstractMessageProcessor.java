/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractMessageProcessor extends AbstractProcessor{
	public static final String ACTIVEMQ_READEN_MESSAGE = "activemq:readenMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2112778445393534126L;

	protected void addMessageForTag(GeneralMessage message, String tag) throws Exception {
		UnreaddenMessage unreaddenMessage = new UnreaddenMessage();
		unreaddenMessage.setMessageDate(message.getDate());
		unreaddenMessage.setMessage(message);
		unreaddenMessage.setTagId(tag);
		DataManagerFactory.getDataManager().sendPackageToRoute(to, unreaddenMessage);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractMessageProcessor other = (AbstractMessageProcessor) obj;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
