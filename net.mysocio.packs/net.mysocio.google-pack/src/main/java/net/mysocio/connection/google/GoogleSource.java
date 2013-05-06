/**
 * 
 */
package net.mysocio.connection.google;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.data.accounts.google.GoogleAccount;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.google.GoogleMessage;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public class GoogleSource extends AccountSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679653964230964105L;

	public Class<?> getMessageClass() {
		return GoogleMessage.class;
	}

	public void createRoute(String to) throws Exception {
		GoogleInputProcessor processor = new GoogleInputProcessor();
		GoogleAccount account = (GoogleAccount)getAccount();
		processor.setTo(to);
		processor.setToken(account.getToken());
		processor.setAccountId(account.getId().toString());
		DataManagerFactory.getDataManager().createRoute("timer://" + getId() + "?fixedRate=true&period=60s", processor, 0l);
	}

	@Override
	public void removeRoute(String userId) throws Exception {
		DataManagerFactory.getDataManager().removeRoute("timer://" + getId() + "?fixedRate=true&period=60s", userId);
	}
}
