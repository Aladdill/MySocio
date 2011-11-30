/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mysocio.connection.readers.IAccountSourceManager;
import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.IMessagesManager;
import net.mysocio.data.ReferenceCountObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public class MessagesManager implements IMessagesManager {
	private static MessagesManager instance = new MessagesManager();
	private static Map<String, ReferenceCountObject<IMessage>> cachedMessages  = new HashMap<String, ReferenceCountObject<IMessage>>();
	
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
		List<Source> sources = user.getSources();
		Integer totalMessages = 0;
		for (Source source : sources) {
			ISourceManager manager = source.getManager();
			List<IMessage> lastMessages = manager.getLastMessages(source, lastUpdate, checkTime);
			List<String> storedMessages = storeMessages(lastMessages);
			if (manager instanceof IAccountSourceManager) {
				IAccountSourceManager accManager = (IAccountSourceManager) manager;
				Map<String, List<String>> messagesByUsers = accManager.orderMessagesByContactsTags(source, lastMessages);
				Set<String> keySet = messagesByUsers.keySet();
				for (String tagId : keySet) {
					user.addUnreadMessages(tagId,messagesByUsers.get(tagId));
				}
			}
			totalMessages += storedMessages.size();
			List<SocioTag> tags = source.getTags();
			for (SocioTag tag : tags) {
				user.addUnreadMessages(tag.getId(),storedMessages);
			}
		}
		user.setLastUpdate(checkTime);
		user.addTotalUnreadmessages(totalMessages);
	}

	public static IMessagesManager getInstance() {
		return instance;
	}

	public synchronized List<String> storeMessages(List<IMessage> messages) {
		List<String> stored = new ArrayList<String>();
		for (IMessage message : messages) {
			IMessage savedMessage = DataManagerFactory.getDataManager().createMessage(message);
			String id = savedMessage.getId();
			ReferenceCountObject<IMessage> cachedMessage = cachedMessages.get(id);
			if (cachedMessage != null){
				cachedMessage.setCounter(cachedMessage.getCounter() + 1);
			}else{
				cachedMessage = new ReferenceCountObject<IMessage>();
				cachedMessage.setCounter(1);
				cachedMessage.setObject(savedMessage);
			}
			cachedMessages.put(id, cachedMessage);
			stored.add(id);
		}
		return stored;
	}

	public List<IMessage> getMessagesForSelectedSource(SocioUser user) {
		List<IMessage> messages = new ArrayList<IMessage>();
		List<String> unreadMessages = user.getUnreadMessages();
		for (String id : unreadMessages) {
			messages.add(cachedMessages.get(id).getObject());
		}
		return messages;
	}
	
	public synchronized void setMessagesReadden(SocioUser user, String messagesId){
		String[] ids = messagesId.split(",");
		for (String id : ids) {
			user.setMessageReadden(id);
			ReferenceCountObject<IMessage> cachedMessage = cachedMessages.remove(id);
			int counter = cachedMessage.getCounter() - 1;
			if (counter == 0){
				DataManagerFactory.getDataManager().deleteObject(cachedMessage.getObject());
			}else{
				cachedMessage.setCounter(counter);
				cachedMessages.put(id, cachedMessage);
			}
		}
	}
}
