/**
 * 
 */
package net.mysocio.data;

import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {
	
	public <T extends ISocioObject> void saveObjects(Class<T> T, List<T> objects) throws Exception;

	public void deleteObject(Object object);

	public SocioUser createUser(Account account, Locale locale) throws Exception;
	
	public Account getAccount(String accountUniqueId);

	public UiObject getUiObject(String category, String name);
	
	public void saveUiObject(UiObject uiObject) throws Exception;

	public<T> T getObject(Class<T> T, String id);
	
	public<T extends ISocioObject> List<T> getObjects(Class<T> T);
	
	public void addAccountToUser(Account account, String userId, UserTags userTags) throws Exception;

	public<T extends ISocioObject> void saveObject(T object) throws Exception;

	public void setMessageReadden(String userId, String messageId);

	public List<GeneralMessage> getUnreadMessages(String userId, String tagId, UserTags tags);

	public String getPage(String userId, String pageKey);

	public CappedCollectionTimeStamp getTimestamp(String collection);

	public UserTags getUserTags(String userId);

	public List<UserAccount> getAccounts(String userId);
	
	public void addSourceToUser(String userId, Source source) throws Exception;

	public Long countUnreadMessages(String userId, String tagId);

	public void sendPackageToRoute(String to, SocioObject object) throws Exception;

	public<T extends GeneralMessage> boolean isNewMessage(String userId, T message, Class<T> T);

	void setMessagesReadden(String userId, String tagId, UserTags tags);

	public<T> void deleteProcessorByField(Class<T> clazz, String fieldName, String fieldValue);

	public abstract Source getSource(String url);

	public boolean isProcessorExist(String userId);

	public abstract UserPermissions getUserPermissions(String mail);
	
	public List<AbstractUserMessagesProcessor> getUserProcessors(String userId);

	public<T> void deleteUserProcessorByField(Class<T> clazz, String fieldName, String fieldValue, String userId);

	public<T extends AbstractUserMessagesProcessor> void saveProcessor(T processor, String uniqueFieldName, String uniqueFieldValue) throws Exception;
	
	public void saveProcessor(AbstractUserMessagesProcessor processor);

	public<T extends GeneralMessage> List<T> getMessagesAfterDate(Class<T> T, Long date, String sourceField, String sourceId);
}
