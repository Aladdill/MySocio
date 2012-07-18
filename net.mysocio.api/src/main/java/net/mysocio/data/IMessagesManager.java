/**
 * 
 */
package net.mysocio.data;

import java.util.List;

import net.mysocio.connection.writers.Destination;
import net.mysocio.data.messages.GeneralMessage;

/**
 * @author Aladdin
 *
 */
public interface IMessagesManager {
	public void postMessage(GeneralMessage message, Destination destination);
	public List<GeneralMessage> getMessagesForSelectedSource(String userId, String tagId);
	public void setMessagesReadden(SocioUser user, String messageId);
	public List<String> storeMessages(List<GeneralMessage> messages);
	public GeneralMessage storeMessage(GeneralMessage message);
}
