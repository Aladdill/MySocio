/**
 * 
 */
package net.mysocio.connection.facebook;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.messages.facebook.FacebookMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class FacebookSource extends AccountSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679653964230964105L;

	public Class<?> getMessageClass() {
		return FacebookMessage.class;
	}

	public void createRoute(String to) throws Exception {
		FacebookInputProcessor processor = new FacebookInputProcessor();
		processor.setTo(to);
		FacebookAccount account = (FacebookAccount)getAccount();
		processor.setToken(account.getToken());
		processor.setUsername(account.getUserName());
		processor.setAccountId(account.getAccountUniqueId());
		CamelContextManager.addRoute("timer://" + getId() + "?fixedRate=true&period=60s", processor, null);
	}
}
