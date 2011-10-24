/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.IMessagesManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public class MessagesManager implements IMessagesManager {
	private static MessagesManager instance = new MessagesManager();
	
	private MessagesManager(){}

	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IMessagesManager#postMessage(net.mysocio.data.messages.IMessage, net.mysocio.connection.writers.IDestination)
	 */
	public void postMessage(IMessage message, IDestination destination) {
		destination.postMessage(message);
	}

	public void updateUnreaddenMessages(SocioUser user) throws Exception {
		long checkTime = System.currentTimeMillis();
		Long lastUpdate = user.getLastUpdate();
		List<ISource> sources = user.getSources();
		for (ISource source : sources) {
			List<String> storedMessages = storeMessages(source.getManager().getLastMessages(source, lastUpdate, checkTime));
			user.addTotalUnreadmessages(storedMessages.size());
			List<SocioTag> tags = source.getTags();
			for (SocioTag tag : tags) {
				user.addUnreadMessages(tag.getId(),storedMessages);
			}
		}
		user.setLastUpdate(checkTime);
		DataManagerFactory.getDataManager().saveObject(user);
	}

	public static IMessagesManager getInstance() {
		return instance;
	}

	private List<String> storeMessages(List<IMessage> messages) {
		List<String> stored = new ArrayList<String>();
		for (IMessage message : messages) {
			stored.add(DataManagerFactory.getDataManager().createMessage(message).getId());
		}
		return stored;
	}

	public List<IMessage> getMessagesForSelectedSource(SocioUser user) {
		return DataManagerFactory.getDataManager().getMessages(user.getUnreadMessages());
	}
	
	public void setMessageReadden(SocioUser user, String messageId){
		user.setMessageReadden(messageId);
		DataManagerFactory.getDataManager().saveObject(user);
		ISocioObject object = DataManagerFactory.getDataManager().getObject(GeneralMessage.class, messageId);
		DataManagerFactory.getDataManager().deleteObject(object);
	}
}
