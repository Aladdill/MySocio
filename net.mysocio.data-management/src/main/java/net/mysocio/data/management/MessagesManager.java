/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.writers.Destination;
import net.mysocio.data.IDataManager;
import net.mysocio.data.IMessagesManager;
import net.mysocio.data.SocioPair;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.camel.UserMessageProcessor;
import net.mysocio.data.messages.GeneralMessage;

/**
 * @author Aladdin
 *
 */
public class MessagesManager implements IMessagesManager {
//	private static final Logger logger = LoggerFactory.getLogger(MessagesManager.class);
	private static MessagesManager instance = new MessagesManager();
	
	private MessagesManager(){}

	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IMessagesManager#postMessage(net.mysocio.data.messages.IMessage, net.mysocio.connection.writers.IDestination)
	 */
	public void postMessage(GeneralMessage message, Destination destination) {
		destination.postMessage(message);
	}

	public static IMessagesManager getInstance() {
		return instance;
	}

	public List<String> storeMessages(List<GeneralMessage> messages) throws Exception {
		List<String> stored = new ArrayList<String>();
		for (GeneralMessage message : messages) {
			storeMessage(message);
			stored.add(message.getId().toString());
		}
		return stored;
	}

	public<T extends GeneralMessage> void storeMessage(T message) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.saveObject(message);
	}

	public List<GeneralMessage> getMessagesForSelectedTag(String userId, String tagId, UserTags tags) {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<GeneralMessage> unreadMessages = dataManager.getUnreadMessages(userId, tagId, tags);
		return unreadMessages;
	}
	@Override
	public void setMessagesReadden(String userId, String messagesId) throws Exception{
		if (messagesId.isEmpty()){
			return;
		}
		String[] ids = messagesId.split(",");
		for (String id : ids) {
			DataManagerFactory.getDataManager().sendPackageToRoute(UserMessageProcessor.ACTIVEMQ_READEN_MESSAGE, new SocioPair(userId, id));
		}
	}
}
