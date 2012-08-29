/**
 * 
 */
package net.mysocio.data;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.UnreaddenMessage;
import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {
	
	public <T extends ISocioObject> void saveObjects(Class<T> T, List<T> objects);

	public void deleteObject(Object object);

	public SocioUser getUser(Account account, Locale locale) throws Exception;
	
	public Account getAccount(String accountUniqueId);

	public UiObject getUiObject(String category, String name);
	
	public void saveUiObject(UiObject uiObject);

	public<T> T getObject(Class<T> T, String id);
	
	public<T extends ISocioObject> List<T> getObjects(Class<T> T);
	
	public void addAccountToUser(Account account, String userId) throws Exception;

	public<T extends ISocioObject> void saveObject(T object);

	public void setMessageReadden(String messageId);

	public List<UnreaddenMessage> getUnreadMessages(SocioUser user, String tagId);

	public String getPage(String userId, String pageKey);

	public SocioTag getTag(String userId, String value);

	public Collection<SocioTag> getUserTags(String userId);

	public List<UserAccount> getAccounts(String userId);
	
	public void addSourceToUser(String userId, Source source) throws Exception;

	public void removeSource(String userId, String sourceId);

	public List<UserContact> getContacts(String userId);

	public List<UserSource> getSources(String userId);

	public Long countUnreadMessages(String tagId);
}
