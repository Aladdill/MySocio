/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;

import net.mysocio.data.IMessage;
import net.mysocio.data.SocioUser;
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

	public void addUnreadMessages(SocioUser user, String sourceId, List<IMessage> messages) {
		if (messages.size() > 0){
			user.addUnreadMessages(sourceId, messages);
			saveObject(user);
		}
	}
}
