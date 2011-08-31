/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;
import java.util.Set;

import net.mysocio.data.SocioUser;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractDataManager implements IDataManager {

	public void addUnreadMessages(SocioUser user, String sourceId, Set<IMessage> messages) {
		if (messages.size() > 0){
			user.addUnreadMessages(sourceId, messages);
			saveObject(user);
		}
	}

	@Override
	public void updateUnreaddenMessages(SocioUser user) {
		Set<IMessage> messages = getMessages(user.getSources(), user);
		user.setLastUpdate(System.currentTimeMillis());
		for (IMessage message : messages) {
			user.addUnreadMessage(message);
		}
		saveObject(user);
	}
}
