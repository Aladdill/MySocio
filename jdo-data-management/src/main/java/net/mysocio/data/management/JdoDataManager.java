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
import javax.jdo.annotations.PersistenceAware;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDataManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.ui.UiObject;
import net.mysocio.data.ui.UserUiObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
@PersistenceAware
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
	
	public void setDetachAllOnCommit(boolean detach){
		pm.setDetachAllOnCommit(detach);
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
//		String userId = account.getUserId();
//		if (userId != null && !userId.isEmpty()) {
//			logger.debug("Account found");
//			return getUniqueObject(SocioUser.class,
//					getEqualsExpression("id", userId));
//		}
		SocioUser user = createUser(account, locale, userName);
		return user;
	}

	private SocioUser createUser(Account account, Locale locale, String userName)
			throws Exception {
		String userId;
		logger.debug("Creating user");
		SocioUser user = new SocioUser();
		user.setName(userName);
		user.setLocale(locale.getLanguage());
		persistObject(user);
		userId = user.getId().toString();
		addAccountToUser(account, user);
//		user.setMainAccount(account);
//		DefaultUserMessagesProcessor processor = new DefaultUserMessagesProcessor();
//		processor.setUser(userId.toString());
//		CamelContextManager.addRoute("activemq:" + userId + ".newMessage", processor, null);
//		MarkMessageReaddenProcessor readdenProcessor = new MarkMessageReaddenProcessor();
//		readdenProcessor.setUserId(userId);
//		CamelContextManager.addRoute("activemq:" + userId + ".messageReaden", readdenProcessor, null);
		logger.debug("User created");
		return user;
	}

	/**
	 * @param account
	 * @param user
	 * @throws Exception 
	 */
	public Account addAccountToUser(Account account, SocioUser user) throws Exception {
//		account.setUserId(user.getId());
		persistObject(account);
		user.addAccount(account);
		List<Source> sources = account.getSources();
		persistObject(user);
		for (Source source : sources) {
//			SourcesManager.addSourceToUser(user, source);
			Source savedSource = createSource(source);
			savedSource.createRoute("activemq:" + user.getId()  + ".newMessage");
			user.addSource(savedSource);
			persistObject(user);
		}
		return account;
	}

	/**
	 * @param account
	 * @return
	 */
	public Account getAccount(String accountUniqueId) {
		Extent<Account> extent = pm.getExtent(Account.class, true);
		Query q = pm.newQuery(extent);
		q.declareParameters("String id");
		q.setFilter("accountUniqueId.equals(id)");
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
		q.setFilter("id.equals(objectId)");
		Map args = new HashMap();
		args.put("objectId", id);
		q.setUnique(true);
		ISocioObject object = (ISocioObject)q.executeWithMap(args);
		return object;
	}

	private String getEqualsExpression(String fieldName, String value) {
		return fieldName + ".equals(\"" + value + "\")";
	}

	private String getMoreExpression(String fieldName, String value) {
		return fieldName + " > \"" + value + "\"";
	}

	private String getLessExpression(String fieldName, String value) {
		return fieldName + " < \"" + value + "\"";
	}

	public UiObject getUiObject(String category, String name) {
		return getUniqueObject(UiObject.class,
				getEqualsExpression("category", category) + " && "
						+ getEqualsExpression("name", name));
	}

	public <T extends ISocioObject> T getUniqueObject(Class<?> T, String query) {
		T object = null;
		Query q = pm.newQuery(T, query);
		q.setUnique(true);
		object = (T) q.execute();
		return object;
	}

//	public <T extends ISocioObject> List<T> getObjects(Class<?> T) {
//		Extent extent = pm.getExtent(T, true);
//		Query q = pm.newQuery(extent);
//		List<T> objects = (List<T>) q.execute();
//		return objects;
//	}

	public void saveUiObject(UiObject uiObject){
		String category = uiObject.getCategory();
		String name = uiObject.getName();
		if (getUiObject(category, name) != null) {
			logger.warn("UI object with name " + name + " already exists in category " + category);
			return;
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
	public Source createSource(Source source) {
		Extent extent = pm.getExtent(Source.class, true);
		Query q = pm.newQuery(extent);
		q.declareParameters("String sourceUrl");
		q.setFilter("url.equals(sourceUrl)");
		Map args = new HashMap();
		args.put("sourceUrl", source.getUrl());
		q.setUnique(true);
		Source object = (Source)q.executeWithMap(args);
		if (object == null){
			return (Source)persistObject(source);
		}
		return (Source)object;
	}

	public GeneralMessage createMessage(GeneralMessage message) {
		GeneralMessage createdMessage = createUniqueObject(message.getClass(),
				getEqualsExpression("uniqueId", message.getUniqueId()), message);
		return createdMessage;
	}

	public Map<String, UiObject> getUserUiObjects(SocioUser user) {
		UserUiObjects objects;
		Query q = pm.newQuery(UserUiObjects.class,
				getEqualsExpression("userId", user.getId().toString()));
		q.setUnique(true);
		objects = (UserUiObjects) q.execute();
		if (objects == null) {
			return new HashMap<String, UiObject>();
		}
		return objects.getUserUiObjects();
	}

	public List<GeneralMessage> getMessages(List<String> ids, String order, int range) {
		Extent extent = pm.getExtent(GeneralMessage.class, true);
		Query q = pm.newQuery(extent);
		q.declareImports("import java.util.Collection");
		q.declareParameters("Collection objectsIds");
		q.setFilter("objectsIds.contains(id)");
		q.setOrdering("date " + order);
		q.setRange(0, range);
		return (List<GeneralMessage>) q.execute(ids);
	}

	@Override
	public <T extends ISocioObject> List<T> getObjects(Class T) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ISocioObject> void saveObject(T object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T getObject(Class T, String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
