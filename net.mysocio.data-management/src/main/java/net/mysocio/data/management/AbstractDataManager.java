/**
 * 
 */
package net.mysocio.data.management;

import java.util.Collections;
import java.util.List;

import net.mysocio.data.IMessage;
import net.mysocio.data.MessagesIdComparator;
import net.mysocio.data.SocioUser;
import net.mysocio.data.UnreaddenMessages;
import net.mysocio.data.UserIdentifier;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractDataManager implements IDataManager {

	protected void setUserIdentifier(SocioUser user, String identifier, String identifierValue) {
		UserIdentifier userIdentifier = UserIdentifier.valueOf(identifier);
		user.setUserIdentifier(userIdentifier);
		switch (userIdentifier) {
		case email:
			user.setEmail(identifierValue);
			break;
		default:
			break;
		}
	}

	public void addUnreadMessages(SocioUser user, String sourceId, List<? extends IMessage> messages) {
		if (messages.size() > 0){
			UnreaddenMessages unreaddenMessages = user.getUnreadMessages(sourceId);
			if (unreaddenMessages == null){
				unreaddenMessages = new UnreaddenMessages();
			}
			Collections.sort(messages, new MessagesIdComparator());
			unreaddenMessages.setLastId(messages.get(0).getId());
			unreaddenMessages.addMessages(messages);
			saveObject(unreaddenMessages);
			user.addUnreadMessages(sourceId, unreaddenMessages);
		}
	}
}
