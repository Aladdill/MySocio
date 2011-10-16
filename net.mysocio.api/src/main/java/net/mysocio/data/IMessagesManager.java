/**
 * 
 */
package net.mysocio.data;

import java.util.List;

import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public interface IMessagesManager {
	public List<IMessage> getLastMessages(SocioUser user) throws Exception;
	public void postMessage(IMessage message, IDestination destination);
	public void updateUnreaddenMessages(SocioUser user) throws Exception;
	public List<IMessage> getMessagesForSelectedSource(SocioUser user);
	public void setMessageReadden(SocioUser user, String sourceId, String messageId);
}
