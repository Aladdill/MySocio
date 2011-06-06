/**
 * 
 */
package net.mysocio.data.management;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Locale;

import net.mysocio.data.Account;
import net.mysocio.data.IMessage;
import net.mysocio.data.SocioUser;
import net.mysocio.sources.rss.RssSource;

import org.junit.Test;

/**
 * @author Aladdin
 *
 */
public class ObjectManagementTest {

	private static final String IDENTIFIER_VALUE = "test@test.com";
	private static final String IDENTIFIER = "email";

	@Test
	public void checkUserPersistency() {
		try {
			IDataManager dataManager = DataManagerFactory.getDataManager();
			SocioUser user = dataManager.createUser(IDENTIFIER, IDENTIFIER_VALUE, Locale.ENGLISH);
			RssSource source = new RssSource();
			source.setUrl("http://test.com/test");
			source.setName("test rss source");
			dataManager.saveObject(source);
			user.addSource(source);
			dataManager.saveObject(user);
			Account acc = new Account();
			acc.setName("test");
			acc.setPassword("test");
			acc.setUserName("testUn");
			dataManager.saveObject(acc);
			user.addAccount(acc);
			dataManager.saveObject(user);
			SocioUser persistentUser = dataManager.getUser(IDENTIFIER, IDENTIFIER_VALUE);
			List<IMessage> unreadMessages = persistentUser.getAllUnreadMessages();
			System.out.println(unreadMessages.isEmpty());
			dataManager.deleteObject(user);
			dataManager.deleteObject(source);
			dataManager.deleteObject(acc);
		}catch(Throwable e){
			fail("User creation failed");
		}
	}
}
