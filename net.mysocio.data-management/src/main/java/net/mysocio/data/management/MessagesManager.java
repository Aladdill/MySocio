/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mysocio.connection.readers.IAccountSourceManager;
import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.IMessagesManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.IMessage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

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
			List<SocioTag> tags = source.getTags();
			int addedMessages = 0;
			for (SocioTag tag : tags) {
				addedMessages = Math.max(addedMessages, user.addUnreadMessages(tag.getId(),storedMessages));
			}
			totalMessages += addedMessages;
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
			cacheMessage(savedMessage);
			stored.add(savedMessage.getId());
		}
		return stored;
	}

	public List<IMessage> getMessagesForSelectedSource(SocioUser user) {
		List<IMessage> messages = new ArrayList<IMessage>();
		List<String> unreadMessages = user.getUnreadMessages();
		for (String id : unreadMessages) {
			IMessage message = getCacheMessage(id);
//			if (message != null){
//				messages.add(message);
//			}else{
				message = (GeneralMessage)DataManagerFactory.getDataManager(user).getObject(id);
				messages.add(message);
//			}
		}
		return messages;
	}
	
	public synchronized void setMessagesReadden(SocioUser user, String messagesId){
		if (messagesId.isEmpty()){
			return;
		}
		String[] ids = messagesId.split(",");
		for (String id : ids) {
			user.setMessageReadden(id);
		}
	}
	private void cacheMessage(IMessage message){
		CacheManager cm = CacheManager.create();
		Cache cache = cm.getCache("Messages");
		Element element = new Element(message.getId(), message);
		cache.put(element);
	}
	
	private IMessage getCacheMessage(String id){
		CacheManager cm = CacheManager.create();
		Cache cache = cm.getCache("Messages");
		Element element = cache.get(id);
		if (element == null){
			return null;
		}
		return (IMessage)element.getValue();
	}
}
