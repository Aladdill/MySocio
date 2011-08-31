/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.IUiObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.IMessage;

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
	
	public GeneralMessage createMessage(GeneralMessage message);
	
	public<T extends ISocioObject> List<T> getObjects(Class<?> T, SocioUser user);

	public SocioTag createTag(SocioTag tag, SocioUser user);

	public void updateUnreaddenMessages(SocioUser user);

	public Set<IMessage> getMessages(Set<ISource> sources, SocioUser user);

	public<T extends Object> List<T> getObjectsWithoutTags(Class<?> T);

	public<T extends Object> T getUniqueObjectWithoutTags(Class<?> T, String query);
}
