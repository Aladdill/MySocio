/**
 * 
 */
package net.mysocio.connection.facebook;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.facebook.FacebookMessage;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
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
		FacebookAccount account = (FacebookAccount)getAccount();
		processor.setTo(to);
		processor.setToken(account.getToken());
		processor.setAccountId(account.getId().toString());
		DataManagerFactory.getDataManager().createRoute("timer://" + getId() + "?fixedRate=true&period=60s", processor, null, 0l);
	}

	@Override
	public void removeRoute(String userId) throws Exception {
		DataManagerFactory.getDataManager().removeRoute("timer://" + getId() + "?fixedRate=true&period=60s", userId);
	}
}
