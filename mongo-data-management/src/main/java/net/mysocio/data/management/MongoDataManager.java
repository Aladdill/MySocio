/**
 * 
 */
package net.mysocio.data.management;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDataManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.ui.UiObject;
import net.mysocio.data.ui.UserUiObjects;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;

/**
 * @author Aladdin
 * 
 */
public class MongoDataManager implements IDataManager {
	private static final Logger logger = LoggerFactory.getLogger(MongoDataManager.class);
	private Datastore ds;
	

	public MongoDataManager(Datastore ds) {
		this.ds = ds; 
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
		SocioUser user = account.getUser();
		if (user != null) {
			logger.debug("Account found");
			return user;
		}
		user = createUser(account, locale, userName);
		return user;
	}

	private SocioUser createUser(Account account, Locale locale, String userName)
			throws Exception {
		String userId;
		logger.debug("Creating user");
		SocioUser user = new SocioUser();
		user.setName(userName);
		user.setLocale(locale.getLanguage());
		ds.save(user);
		userId = user.getId().toString();
		addAccountToUser(account, user);
		user.setMainAccount(account);
		DefaultUserMessagesProcessor processor = new DefaultUserMessagesProcessor();
		processor.setUser(user);
		CamelContextManager.addRoute("activemq:" + userId + ".newMessage", processor, null);
		MarkMessageReaddenProcessor readdenProcessor = new MarkMessageReaddenProcessor();
		readdenProcessor.setUser(user);
		CamelContextManager.addRoute("activemq:" + userId + ".messageReaden", readdenProcessor, null);
		logger.debug("User created");
		return user;
	}

	/**
	 * @param account
	 * @param user
	 * @throws Exception 
	 */
	public Account addAccountToUser(Account account, SocioUser user) throws Exception {
		account.setUser(user);
		saveObject(account);
		user.addAccount(account);
		saveObject(user);
		List<Source> sources = account.getSources();
		for (Source source : sources) {
			Source savedSource = createSource(source);
			savedSource.createRoute("activemq:" + user.getId()  + ".newMessage");
			user.addSource(savedSource);
			saveObject(user);
		}
		return account;
	}

	/**
	 * @param account
	 * @return
	 */
	public Account getAccount(String accountUniqueId) {
		Query<Account> q = ds.createQuery(Account.class).field("accountUniqueId").equal(accountUniqueId);
		return q.get();
	}

	public<T> T getObject(Class T, String id) {
		Query<T>  q = (Query<T>)ds.createQuery(T).field("id").equal(new ObjectId(id));
		return q.get();
	}

	public UiObject getUiObject(String category, String name) {
		Query<UiObject>  q = ds.createQuery(UiObject.class).field("category").equal(category)
				.field("name").equal(name);
		return q.get();
	}

	public <T extends ISocioObject> List<T> getObjects(Class T) {
		return ds.find(T).asList();
	}

	public void saveUiObject(UiObject uiObject){
		String category = uiObject.getCategory();
		String name = uiObject.getName();
		if (getUiObject(category, name) != null) {
			logger.warn("UI object with name " + name + " already exists in category " + category);
			return;
		}
		saveObject(uiObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mysocio.data.management.IDataManager#saveObjects(java.util.List)
	 */
	public void saveObjects(List<? extends Object> objects) {
		ds.save(objects);
	}

	public void deleteObject(Object object) {
		ds.delete(object);
	}

	public <T> T createUniqueObject(Query<T> q,T object) {
		T objectT = (T) q.get();
		if (objectT == null) {
			ds.save(object);
		} else {
			logger.info("Duplicate object of type: " + object.getClass()
					+ " for query: " + q.toString());
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
		Query<Source> q = ds.createQuery(Source.class).field("url").equal(source.getUrl());
		Source object = q.get();
		for (SocioTag tag : source.getTags()) {
			ds.save(tag);
		}
		if (object == null){
			ds.save(source);
			return source;
		}
		return (Source)object;
	}

	public GeneralMessage createMessage(GeneralMessage message) {
		Query<GeneralMessage> q = ds.createQuery(GeneralMessage.class).field("uniqueId").equal(message.getUniqueId());
		return createUniqueObject(q, message);
	}

	public Map<String, UiObject> getUserUiObjects(SocioUser user) {
		UserUiObjects objects;
		Query<UserUiObjects> q = ds.createQuery(UserUiObjects.class).field("userId").equal(user.getId().toString());
		objects = q.get();
		if (objects == null) {
			return new HashMap<String, UiObject>();
		}
		return objects.getUserUiObjects();
	}

	public List<GeneralMessage> getMessages(List<String> ids, String order, int range) {
		Query<GeneralMessage> q = ds.createQuery(GeneralMessage.class).filter("id in", ids).order("date").limit(range);
		return q.asList();
	}

	@Override
	public <T extends ISocioObject> void saveObject(T object) {
		ds.save(object);
	}
}
