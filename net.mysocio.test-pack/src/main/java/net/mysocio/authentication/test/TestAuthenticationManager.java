/**
 * 
 */
package net.mysocio.authentication.test;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.JdoDataManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class TestAuthenticationManager implements IAuthenticationManager{
	private static final Logger logger = LoggerFactory.getLogger(TestAuthenticationManager.class);
	
	protected Logger getLogger() {
		return logger;
	}

	public String getRequestUrl() {
		return "execute?command=startAuthentication&flow=addAccount";
	}

	public Account getAccount(IConnectionData connectionData) throws Exception {
		JdoDataManager dataManager = (JdoDataManager)DataManagerFactory.getDataManager();
		Account account = dataManager.getAccount(TestAccount.class, "test@test.com");
		if (account == null){
			account = new TestAccount();
			account.setAccountUniqueId("test@test.com");
			account.setUserName("Vasya Pupkin");
			account.setUserpicUrl("images/portrait.jpg");
		}
		return account;
	}
}
