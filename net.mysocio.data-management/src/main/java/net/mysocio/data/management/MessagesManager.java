/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.IMessagesManager;
import net.mysocio.data.ISocioObject;
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

	public void updateUnreaddenMessages(SocioUser user) {
		List<IMessage> messages = getLastMessages(user);
		storeMessages(messages);
		for (IMessage message : messages) {
			user.addUnreadMessage(message.getSourceId(), message.getId());
		}
		DataManagerFactory.getDataManager().saveObject(user);
	}

	public List<IMessage> getLastMessages(SocioUser user) {
		long checkTime = System.currentTimeMillis();
		Long lastUpdate = user.getLastUpdate();
		Set<ISource> sources = user.getSources();
		List<IMessage> messages = new ArrayList<IMessage>();
		for (ISource source : sources) {
			messages.addAll(source.getManager().getLastMessages(source, lastUpdate, checkTime));
		}
		user.setLastUpdate(checkTime);
		return messages;
	}

	public static IMessagesManager getInstance() {
		return instance;
	}

	private void storeMessages(List<IMessage> messages) {
		for (IMessage message : messages) {
			DataManagerFactory.getDataManager().createMessage(message);
		}
	}

	public List<IMessage> getMessagesForSelectedSource(SocioUser user) {
		return DataManagerFactory.getDataManager().getMessages(user.getUnreadMessages());
	}
	
	public void setMessageReadden(SocioUser user, String sourceId, String messageId){
		user.setMessageReadden(sourceId, messageId);
		DataManagerFactory.getDataManager().saveObject(user);
		ISocioObject object = DataManagerFactory.getDataManager().getObject(GeneralMessage.class, messageId);
		DataManagerFactory.getDataManager().deleteObject(object);
	}
}
