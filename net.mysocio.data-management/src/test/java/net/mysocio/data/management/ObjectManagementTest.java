/**
 * 
 */
package net.mysocio.data.management;

import static org.junit.Assert.fail;

import java.util.Locale;

import net.mysocio.data.Account;
import net.mysocio.data.SocioUser;
import net.mysocio.sources.rss.RssSource;

import org.junit.Test;

/**
 * @author Aladdin
 *
 */
public class ObjectManagementTest {

	@Test
	public void checkUserPersistency() {
		try {
			IDataManager dataManager = DataManagerFactory.getDataManager();
			SocioUser user = dataManager.createUser("email", "test@test.com", Locale.ENGLISH);
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
			dataManager.deleteObject(user);
			dataManager.deleteObject(source);
			dataManager.deleteObject(acc);
		}catch(Throwable e){
			fail("User creation failed");
		}
	}
}
