/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;

import net.mysocio.data.IMessage;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractDataManager implements IDataManager {

	public void addUnreadMessages(SocioUser user, String sourceId, List<IMessage> messages) {
		if (messages.size() > 0){
			user.addUnreadMessages(sourceId, messages);
			saveObject(user);
		}
	}

	@Override
	public void updateUnreaddenMessages(SocioUser user) {
		List<IMessage> messages = getMessages(user.getSources(), user.getLastUpdate());
		for (IMessage message : messages) {
			user.addUnreadMessage(message);
		}
	}
}
