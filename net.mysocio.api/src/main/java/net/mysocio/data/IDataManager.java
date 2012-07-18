/**
 * 
 */
package net.mysocio.data;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	
	public void saveObjects(List<? extends Object> objects);

	public void deleteObject(Object object);

	public SocioUser getUser(Account account, Locale locale) throws Exception;
	
	public Account getAccount(String accountUniqueId);

	public Map<String, UiObject> getUserUiObjects(SocioUser user);
	
	public UiObject getUiObject(String category, String name);
	
	public void saveUiObject(UiObject uiObject);

	public Source createSource(Source source);
	
	public GeneralMessage createMessage(GeneralMessage message);
	
	public List<GeneralMessage> getMessages(List<String> ids, String order, int range);
	
	public<T> T getObject(Class T, String id);
	
	public<T extends ISocioObject> List<T> getObjects(Class T);
	
	public Account addAccountToUser(Account account, SocioUser user) throws Exception;

	public<T extends ISocioObject> void saveObject(T object);

	public void setMessageReadden(String messageId, String userId);

	public List<GeneralMessage> getUnreadMessages(String userId, String tagId);

	public String getPage(String userId, String pageKey);

	public SocioTag getTag(String userId, String value);

	public List<UnreaddenMessage> getUnreadMessages(String userId);

	public Collection<SocioTag> getUserTags(String userId);
}
