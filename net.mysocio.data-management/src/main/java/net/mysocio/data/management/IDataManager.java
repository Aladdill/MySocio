/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.IMessage;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.IUiObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {

	public abstract SocioUser createUser(String identifier, String identifierValue, Locale locale);

	public abstract void saveObjects(List<? extends ISocioObject> objects);

	public abstract void saveObject(ISocioObject object);
	
	public abstract void deleteObject(ISocioObject object);

	public abstract void addUnreadMessages(SocioUser user, String sourceId, List<IMessage> messages);

	public abstract List<IMessage> getMessages(ISource source, Long date);

	public abstract SocioUser getUser(String identifier, String identifierValue);

	public abstract Map<String, IUiObject> getUserUiObjects(SocioUser user);

	public abstract Source createSource(Source source);
	
	public<T> List<T> getObjects(Class T);

	public abstract SocioTag createTag(SocioTag tag);
}
