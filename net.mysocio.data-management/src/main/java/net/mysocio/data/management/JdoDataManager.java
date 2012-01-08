/**
 * 
 */
package net.mysocio.data.management;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDataManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.IMessage;
import net.mysocio.data.ui.IUiObject;
import net.mysocio.data.ui.UiObject;
import net.mysocio.data.ui.UserUiObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
public class JdoDataManager implements IDataManager {
	private static final Logger logger = LoggerFactory.getLogger(JdoDataManager.class);
	private PersistenceManager pm;
	

	public JdoDataManager(PersistenceManager pm) {
		this.pm = pm; 
	}

	public void flush() {
		pm.flush();
	}

	public Transaction startTransaction() {
		Transaction tx = pm.currentTransaction();
		if (!tx.isActive()){
			tx.begin();
		}
		return tx;
	}

	public void endTransaction(Transaction tx) {
		if (tx.isActive()){
			tx.commit();
		}
	}
	public void rollBackTransaction(Transaction tx) {
		if (tx.isActive()) {
			tx.rollback();
		}
	}

	public <T> T detachObject(T object) {
		return pm.detachCopy(object);
	}

	public <T> T persistObject(T object) {
		return pm.makePersistent(object);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.mysocio.data.management.IDataManager#createUser(java.lang.String,
	 * java.lang.String)
	 */
	public SocioUser getUser(Account account, Locale locale) throws Exception {
		String userName = account.getUserName();
		logger.debug("Getting user " + userName + " for "
				+ account.getAccountType());
		String userId = account.getUserId();
		if (userId != null && !userId.isEmpty()) {
			logger.debug("Account found");
			return getUniqueObject(SocioUser.class,
					getEqualsExpression("id", userId));
		}
		logger.debug("Creating user");
		SocioUser user = new SocioUser();
		user.setName(userName);
		user.setLocale(locale.getLanguage());
		persistObject(user);
		UserUiObjects uiObjects = new UserUiObjects();
		userId = user.getId();
		uiObjects.setUserId(userId);
		persistObject(uiObjects);
		addAccountToUser(account, user);
		DefaultUserMessagesProcessor processor = new DefaultUserMessagesProcessor();
		processor.setUserId(userId);
		CamelContextManager.addRoute("activemq:" + userId, processor, null);
		logger.debug("User created");
		return user;
	}

	/**
	 * @param account
	 * @param user
	 * @throws Exception 
	 */
	public Account addAccountToUser(Account account, SocioUser user) throws Exception {
		account.setUserId(user.getId());
		persistObject(account);
		user.addAccount(account);
		user.setMainAccount(account);
		List<Source> sources = account.getSources();
		for (Source source : sources) {
			SocioTag tag = new SocioTag();
			tag.setValue("facebook.tag");
			tag.setUniqueId("facebook.tag");
			tag.setIconType("facebook.icon.general");
			source.addTag(tag);
			SocioTag tag1 = new SocioTag();
			tag1.setValue(account.getUserName());
			tag1.setUniqueId(account.getAccountUniqueId());
			tag1.setIconType("facebook.icon.general");
			source.addTag(tag1);
			SourcesManager.addSourceToUser(user, source);
		}
		return account;
	}

	/**
	 * @param account
	 * @return
	 */
	public Account getAccount(Class clazz, String accountUniqueId) {
		Query q = pm.newQuery(clazz);
		q.declareParameters("String id");
		q.setFilter("accountUniqueId == id");
		Map args = new HashMap();
		args.put("id", accountUniqueId);
		q.setUnique(true);
		Account object = (Account)q.executeWithMap(args);
		return object;
	}

	public ISocioObject getObject(String id) {
		Extent e = pm.getExtent(SocioObject.class, true);
		Query  q = pm.newQuery(e);
		q.declareParameters("String objectId");
		q.setFilter("id == objectId");
		Map args = new HashMap();
		args.put("objectId", id);
		q.setUnique(true);
		ISocioObject object = (ISocioObject)q.executeWithMap(args);
		return object;
	}

	private String getEqualsExpression(String fieldName, String value) {
		return fieldName + " == \"" + value + "\"";
	}

	private String getMoreExpression(String fieldName, String value) {
		return fieldName + " > \"" + value + "\"";
	}

	private String getLessExpression(String fieldName, String value) {
		return fieldName + " < \"" + value + "\"";
	}

	public IUiObject getUiObject(String category, String name) {
		return getUniqueObject(UiObject.class,
				getEqualsExpression("category", category) + " && "
						+ getEqualsExpression("name", name));
	}

	public <T extends ISocioObject> T getUniqueObject(Class<?> T, String query) {
		T object;
		Query q = pm.newQuery(T, query);
		q.setUnique(true);
		object = (T) q.execute();
		return object;
	}

	public <T extends ISocioObject> List<T> getObjects(Class<?> T) {
		Query q = pm.newQuery(T);
		List<T> objects = (List<T>) q.execute();
		return objects;
	}

	public void saveUiObject(UiObject uiObject)
			throws DuplicateMySocioObjectException {
		String category = uiObject.getCategory();
		String name = uiObject.getName();
		if (getUiObject(category, name) != null) {
			throw new DuplicateMySocioObjectException("UI object with name "
					+ name + " already exists in category " + category);
		}
		persistObject(uiObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mysocio.data.management.IDataManager#saveObjects(java.util.List)
	 */
	public void saveObjects(List<? extends Object> objects) {
		for (Object object : objects) {
			persistObject(object);
		}
	}

	public void deleteObject(Object object) {
		PersistenceManager pm = JDOHelper.getPersistenceManager(object);
		pm.deletePersistent(object);
	}

	public <T> T createUniqueObject(Class T, String query, T object) {
		Extent<T> extent = pm.getExtent(T, true);
		Query q = pm.newQuery(extent, query);
		q.setUnique(true);
		T objectT = (T) q.execute();
		if (objectT == null) {
			objectT = persistObject(object);
		} else {
			logger.info("Duplicate object of type: " + T.toString()
					+ " for query: " + query);
		}
		return objectT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.mysocio.data.management.IDataManager#saveSource(net.mysocio.connection
	 * .readers.ISource)
	 */
	public Source createSource(ISource source) {
		Query q = pm.newQuery(source.getClass());
		q.declareParameters("String sourceUrl");
		q.setFilter("url == sourceUrl");
		Map args = new HashMap();
		args.put("sourceUrl", source.getUrl());
		q.setUnique(true);
		ISource object = (ISource)q.executeWithMap(args);
		if (object == null){
			return (Source)persistObject(source);
		}
		return (Source)object;
	}

	public IMessage createMessage(IMessage message) {
		IMessage createdMessage = createUniqueObject(message.getClass(),
				getEqualsExpression("uniqueId", message.getUniqueId()), message);
		return createdMessage;
	}

	public Map<String, IUiObject> getUserUiObjects(SocioUser user) {
		UserUiObjects objects;
		Query q = pm.newQuery(UserUiObjects.class,
				getEqualsExpression("userId", user.getId()));
		q.setUnique(true);
		objects = (UserUiObjects) q.execute();
		if (objects == null) {
			return new HashMap<String, IUiObject>();
		}
		return objects.getUserUiObjects();
	}

	public List<IMessage> getMessages(List<String> ids) {
		Query q = pm.newQuery(GeneralMessage.class);
		q.declareImports("import java.util.Collection");
		q.declareParameters("Collection objectsIds");
		q.setFilter("objectsIds.contains(id)");
		q.setOrdering("date ascending");
		return (List<IMessage>) q.execute(ids);
	}
}
