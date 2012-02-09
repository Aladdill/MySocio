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
import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public interface IDataManager {
	public Transaction startTransaction();
	
	public void endTransaction(Transaction tx);
	
	public void rollBackTransaction(Transaction tx);
	
	public void setDetachAllOnCommit(boolean detach);

	public void saveObjects(List<? extends Object> objects);

	public<T> T detachObject(T object);
	
	public<T> T persistObject(T object);
	
	public void deleteObject(Object object);

	public SocioUser getUser(Account account, Locale locale) throws Exception;
	
	public Account getAccount(String accountUniqueId);

	public Map<String, UiObject> getUserUiObjects(SocioUser user);
	
	public UiObject getUiObject(String category, String name);
	
	public void saveUiObject(UiObject uiObject);

	public ISource createSource(ISource source);
	
	public IMessage createMessage(IMessage message);
	
	public List<IMessage> getMessages(List<String> ids, String order, int range);
	
	public ISocioObject getObject(String id);
	
	public<T extends ISocioObject> List<T> getObjects(Class<?> T);
	
	public Account addAccountToUser(Account account, SocioUser user) throws Exception;
	
	public void flush();
}
