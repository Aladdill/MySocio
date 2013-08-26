/**
 * 
 */
package net.mysocio.connection.vkontakte;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.data.accounts.vkontakte.VkontakteAccount;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.vkontakte.VkontakteMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public class VkontakteSource extends AccountSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679653964230964105L;

	public Class<?> getMessageClass() {
		return VkontakteMessage.class;
	}

	public void createProcessor(String userId) throws Exception {
		VkontakteInputProcessor processor = new VkontakteInputProcessor();
		VkontakteAccount account = (VkontakteAccount)getAccount();
		processor.setToken(account.getToken());
		String accountId = account.getId().toString();
		processor.setAccountId(accountId);
		processor.setUserId(userId);
		DataManagerFactory.getDataManager().saveProcessor(processor, "accountId", getAccount().getId().toString());
	}

	@Override
	public void removeProcessor(String userId) throws Exception {
		DataManagerFactory.getDataManager().deleteProcessorByField(VkontakteInputProcessor.class, "accountId", getAccount().getId().toString());
	}
}
