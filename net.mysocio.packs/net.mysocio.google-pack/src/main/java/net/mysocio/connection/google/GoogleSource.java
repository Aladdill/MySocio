/**
 * 
 */
package net.mysocio.connection.google;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.data.accounts.google.GoogleAccount;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.google.GoogleMessage;

import com.google.code.morphia.annotations.Entity;

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

	public void createProcessor(String userId) throws Exception {
		GoogleInputProcessor processor = new GoogleInputProcessor();
		GoogleAccount account = (GoogleAccount)getAccount();
		processor.setToken(account.getToken());
		processor.setAccountId(account.getId().toString());
		processor.setUserId(userId);
		DataManagerFactory.getDataManager().saveProcessor(processor, "accountId", getAccount().getId().toString());
	}

	@Override
	public void removeProcessor(String userId) throws Exception {
		DataManagerFactory.getDataManager().deleteProcessorByField(GoogleInputProcessor.class, "accountId", getAccount().getId().toString());
	}
}
