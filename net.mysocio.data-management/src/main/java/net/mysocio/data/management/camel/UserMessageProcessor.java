/**
 * 
 */
package net.mysocio.data.management.camel;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.AbstractUserMessagesProcessor;
import net.mysocio.data.IDataManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
public abstract class UserMessageProcessor extends AbstractUserMessagesProcessor{
	public static final String ACTIVEMQ_READEN_MESSAGE = "activemq:readenMessage";
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(UserMessageProcessor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -2112778445393534126L;

	protected void addMessageForTag(GeneralMessage message, String tag) throws Exception {
		String userId = getUserId();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		if (dataManager.isNewMessage(userId, message)){
			dataManager.saveObject(createUnreaddenMessage(message, tag, userId));
		}
	}
	
	protected void addMessagesForTag(List<GeneralMessage> messages, String tag) throws Exception {
		List<UnreaddenMessage> unreaddenMessages = new ArrayList<UnreaddenMessage>();
		String userId = getUserId();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		for (GeneralMessage generalMessage : messages) {
			if (dataManager.isNewMessage(userId, generalMessage)){
				unreaddenMessages.add(createUnreaddenMessage(generalMessage, tag, userId));
			}
		}
		if (!unreaddenMessages.isEmpty()){
			dataManager.saveObjects(UnreaddenMessage.class, unreaddenMessages);
		}
	}

	private UnreaddenMessage createUnreaddenMessage(GeneralMessage message,
			String tag, String userId) {
		UnreaddenMessage unreaddenMessage = new UnreaddenMessage();
		unreaddenMessage.setMessageDate(message.getDate());
		unreaddenMessage.setMessage(message);
		unreaddenMessage.setTagId(tag);
		unreaddenMessage.setUserId(userId);
		if (logger.isDebugEnabled()){
			logger.debug("Got message with uid " + message.getUniqueFieldValue().toString());
		}
		return unreaddenMessage;
	}
}
