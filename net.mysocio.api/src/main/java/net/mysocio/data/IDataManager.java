/**
 * 
 */
package net.mysocio.data;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jdo.Transaction;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.IMessage;
import net.mysocio.data.ui.IUiObject;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {
	public Transaction startTransaction();
	
	public void endTransaction(Transaction tx);
	
	public void rollBackTransaction(Transaction tx);

	public void saveObjects(List<? extends Object> objects);

	public Object saveObject(Object object);
	
	public<T> T detachObject(T object);
	
	public<T> T persistObject(T object);
	
	public void deleteObject(Object object);

	public SocioUser getUser(Account account, Locale locale);
	
	public Account getAccount(Class clazz, String accountUniqueId);

	public Map<String, IUiObject> getUserUiObjects(SocioUser user);

	public ISource createSource(ISource source);
	
	public ISource addSourceToUser(SocioUser user, List<SocioTag> accTags, ISource source);
	
	public IMessage createMessage(IMessage message);
	
	public List<IMessage> getMessages(List<String> ids);
	
	public ISocioObject getObject(String id);
	
	public<T extends ISocioObject> List<T> getObjects(Class<?> T);
	
	public Account addAccountToUser(Account account, SocioUser user);
	
	public List<IMessage> getSourceAwareMessages(String id, Long from, Long to);
	
	public void flush();
}
