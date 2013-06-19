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

	public void createProcessor(String userId) throws Exception {
		FacebookMessagesProcessor processor = new FacebookMessagesProcessor();
		FacebookAccount account = (FacebookAccount)getAccount();
		processor.setToken(account.getToken());
		processor.setAccountId(account.getId().toString());
		processor.setUserId(userId);
		DataManagerFactory.getDataManager().saveProcessor(processor,"accountId", getAccount().getId().toString());
	}

	@Override
	public void removeProcessor(String userId) throws Exception {
		DataManagerFactory.getDataManager().deleteProcessorByField(FacebookMessagesProcessor.class, "accountId", getAccount().getId().toString());
	}
}
