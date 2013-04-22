/**
 * 
 */
package net.mysocio.data;

import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;
import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {
	
	public <T extends ISocioObject> void saveObjects(Class<T> T, List<T> objects) throws Exception;

	public void deleteObject(Object object);

	public SocioUser getUser(Account account, Locale locale) throws Exception;
	
	public Account getAccount(String accountUniqueId);

	public UiObject getUiObject(String category, String name);
	
	public void saveUiObject(UiObject uiObject) throws Exception;

	public<T> T getObject(Class<T> T, String id);
	
	public<T extends ISocioObject> List<T> getObjects(Class<T> T);
	
	public void addAccountToUser(Account account, String userId, UserTags userTags) throws Exception;

	public<T extends ISocioObject> void saveObject(T object) throws Exception;

	public void setMessageReadden(String userId, String messageId);

	public List<UnreaddenMessage> getUnreadMessages(SocioUser user, String tagId, UserTags tags);

	public String getPage(String userId, String pageKey);

	public CappedCollectionTimeStamp getTimestamp(String collection);

	public UserTags getUserTags(String userId);

	public List<UserAccount> getAccounts(String userId);
	
	public void addSourceToUser(String userId, Source source) throws Exception;

	public Long countUnreadMessages(String tagId);

	public void createRoute(String from, AbstractProcessor processor, String to, Long delay) throws Exception;
	
	public void sendPackageToRoute(String to, SocioObject object) throws Exception;

	boolean isNewMessage(String userId, GeneralMessage message);

	void setMessagesReadden(String userId, String tagId, UserTags tags);

	public abstract void removeRoute(String from, String userId);

	public abstract Source getSource(String url);

	public boolean isRouteExist(String from, String to);
}
