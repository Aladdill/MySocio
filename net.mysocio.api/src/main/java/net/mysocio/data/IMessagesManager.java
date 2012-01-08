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
	public void postMessage(IMessage message, IDestination destination);
	public List<IMessage> getMessagesForSelectedSource(SocioUser user);
	public void setMessagesReadden(SocioUser user, String messageId);
	public List<String> storeMessages(List<IMessage> messages);
	public IMessage storeMessage(IMessage message);
}
