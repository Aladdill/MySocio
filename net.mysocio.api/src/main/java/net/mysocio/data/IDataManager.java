/**
 * 
 */
package net.mysocio.data;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.IMessage;
import net.mysocio.data.ui.IUiObject;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {

	public void saveObjects(List<? extends Object> objects);

	public void saveObject(Object object);
	
	public void deleteObject(Object object);

	public SocioUser getUser(Account account, Locale locale);

	public Map<String, IUiObject> getUserUiObjects(SocioUser user);

	public Source createSource(Source source, SocioUser user);
	
	public IMessage createMessage(IMessage message);
	
	public List<IMessage> getMessages(List<String> ids);
	
	public<T extends ISocioObject> List<T> getObjects(Class<?> T, SocioUser user);

	public SocioTag createTag(SocioTag tag, SocioUser user);

	public<T extends Object> List<T> getObjectsWithoutTags(Class<?> T);

	public<T extends Object> T getUniqueObjectWithoutTags(Class<?> T, String query);
	
	public ISocioObject getObject(Class<?> clazz, String id);
}
