/**
 * 
 */
package net.mysocio.authentication.test;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.TestAccount;
import net.mysocio.data.management.DataManagerFactory;

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
		return "execute?command=addAccount";
	}

	public Account getAccount(IConnectionData connectionData) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		Account account = dataManager.getAccount("test@test.com");
		if (account == null){
			account = new TestAccount();
			account.setAccountUniqueId("test@test.com");
			account.setUserName("Vasya Pupkin");
			account.setUserpicUrl("images/portrait.jpg");
		}
		return account;
	}
}
