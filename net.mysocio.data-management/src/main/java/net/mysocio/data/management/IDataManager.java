/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.SourcesGroup;
import net.mysocio.data.Contact;
import net.mysocio.data.IMessage;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.IUiObject;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {

	public abstract SocioUser createUser(String identifier, String identifierValue, Locale locale);

	public abstract void saveObjects(List<? extends ISocioObject> objects);

	public abstract void saveObject(ISocioObject object);

	public abstract void saveSource(ISource source);

	public abstract void saveSourcesGroup(SourcesGroup sourcesGroup);

	public abstract void saveContact(Contact contact);

	public abstract void saveUser(SocioUser user);

	public abstract List<ISource> getSources(Class clazz);

	public abstract ISource getSource(Long id);

	public abstract void addUnreadMessages(SocioUser user, Long sourceId,
			List<? extends IMessage> messages);

	public abstract List<IMessage> getMessages(ISource source, Long firstId);

	public abstract SocioUser getUser(String identifier, String identifierValue);

	public abstract Map<String, IUiObject> getUserUiObjects(SocioUser user);

}
