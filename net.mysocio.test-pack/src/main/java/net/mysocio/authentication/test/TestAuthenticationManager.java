/**
 * 
 */
package net.mysocio.authentication.test;

import java.util.Locale;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.JdoDataManager;
import net.mysocio.data.management.MessagesManager;

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

	public String authenticate(IConnectionData connectionData) throws Exception {
		Account account = new TestAccount();
		account.setAccountUniqueId("test@test.com");
		account.setUserName("Vasya Pupkin");
		account.setUserpicUrl("images/portrait.jpg");
		JdoDataManager dataManager = (JdoDataManager)DataManagerFactory.getDataManager();
		SocioUser user = dataManager.getUser(account, new Locale("ru"));
		MessagesManager.getInstance().updateUnreaddenMessages(user);
		connectionData.setUser(user);
		connectionData.removeSessionAttribute("identifier");
		return "pages/closingWindow.html";
	}
}
