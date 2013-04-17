/**
 * 
 */
package net.mysocio.data;

import java.util.List;

import net.mysocio.connection.writers.Destination;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

/**
 * @author Aladdin
 *
 */
public interface IMessagesManager {
	public void postMessage(GeneralMessage message, Destination destination);
	public List<UnreaddenMessage> getMessagesForSelectedTag(String userId, String tagId, UserTags tags);
	public void setMessagesReadden(String userId, String messageId) throws Exception;
	public List<String> storeMessages(List<GeneralMessage> messages) throws Exception;
	public void storeMessage(GeneralMessage message) throws Exception;
}
