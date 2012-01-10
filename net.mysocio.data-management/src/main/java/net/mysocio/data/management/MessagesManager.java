/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceAware;

import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.IDataManager;
import net.mysocio.data.IMessagesManager;
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
@PersistenceAware
public class MessagesManager implements IMessagesManager {
	private static MessagesManager instance = new MessagesManager();
	
	private MessagesManager(){}

	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IMessagesManager#postMessage(net.mysocio.data.messages.IMessage, net.mysocio.connection.writers.IDestination)
	 */
	public void postMessage(IMessage message, IDestination destination) {
		destination.postMessage(message);
	}

	public static IMessagesManager getInstance() {
		return instance;
	}

	public List<String> storeMessages(List<IMessage> messages) {
		List<String> stored = new ArrayList<String>();
		for (IMessage message : messages) {
			IMessage savedMessage = storeMessage(message);
			stored.add(savedMessage.getId());
		}
		return stored;
	}

	public IMessage storeMessage(IMessage message) {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.setDetachAllOnCommit(true);
		Transaction transaction = dataManager.startTransaction();
		IMessage savedMessage = dataManager.createMessage(message);
		dataManager.endTransaction(transaction);
		cacheMessage(savedMessage);
		return savedMessage;
	}

	public List<IMessage> getMessagesForSelectedSource(SocioUser user) {
		List<IMessage> messages = new ArrayList<IMessage>();
		List<String> unreadMessages = user.getUnreadMessages();
		for (String id : unreadMessages) {
			IMessage message = getCacheMessage(id);
			if (message != null){
				messages.add(message);
			}else{
				message = (GeneralMessage)DataManagerFactory.getDataManager(user).getObject(id);
				messages.add(message);
			}
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
