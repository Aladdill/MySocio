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
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDataManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.SocioObjectTags;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.contacts.Contact;
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
	private static PersistenceManager pm;
	private static PersistenceManagerFactory pmf;
	private static IDataManager instance = new JdoDataManager();
	
	public static IDataManager getInstance() {
		if (pm == null){
			// Create a PersistenceManagerFactory for this datastore
			System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	        pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	        pm = pmf.getPersistenceManager();
	        pm.setMultithreaded(true);
		}
		return instance;
	}
	
	public static void closeDataConnection(){
		if (pm != null){
			pm.close();
			pmf.close();
		}
	}
	
	private JdoDataManager(){}
	
	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IDataManager#createUser(java.lang.String, java.lang.String)
	 */
	public SocioUser getUser(Account account, Locale locale){
		String userName = account.getUserName();
		logger.debug("Getting user " + userName + " for " + account.getAccountType());
		Account existingAccount = getUniqueObjectWithoutTags(account.getClass(), getEqualsExpression("accountUniqueId", account.getAccountUniqueId()));
		if (existingAccount != null){
			logger.debug("Account found");
			return getUniqueObjectWithoutTags(SocioUser.class, getEqualsExpression("id", existingAccount.getUserId()));
		}
		logger.debug("Creating user");
		SocioUser user = new SocioUser();
		user.setName(userName);
		user.setUserpicUrl(account.getUserpicUrl());
		user.setLocale(locale.getLanguage());
		saveObject(account);
		user.addAccount(account);
		saveObject(user);
		account.setUserId(user.getId());
		saveObject(account);
		logger.debug("User created");
		return user;
	}
	
	public ISocioObject getObject(Class<?> clazz, String id){
		return getUniqueObjectWithoutTags(clazz, getEqualsExpression("id", id));
	}
	
	private String getEqualsExpression(String fieldName, String value){
		return fieldName + " == \"" + value + "\"";
	}

	public IUiObject getUiObject(String category, String name){
        return getUniqueObjectWithoutTags(UiObject.class, getEqualsExpression("category", category) + " && " + getEqualsExpression("name",name));
	}
	
	public<T extends ISocioObject> T getUniqueObject(Class<?> T, String query, SocioUser user){
		T object;
		Query q=pm.newQuery(T, query);
		q.setUnique(true);
		object = (T)q.execute();
		retreiveTags(object, user);
		return object;
	}
	
	public<T extends Object> T getUniqueObjectWithoutTags(Class<?> T, String query){
		T object;
		Query q=pm.newQuery(T, query);
		q.setUnique(true);
		object = (T)q.execute();
		return object;
	}
	
	public<T extends ISocioObject> List<T> getObjects(Class<?> T, SocioUser user){
		List<T> objects;
		Query q=pm.newQuery(T);
		objects = (List<T>)q.execute();
		retreiveTags(objects, user);
		return objects;
	}
	
	public<T extends Object> List<T> getObjectsWithoutTags(Class<?> T){
		List<T> objects;
		Query q=pm.newQuery(T);
		objects = (List<T>)q.execute();
		return objects;
	}
	
	public void saveUiObject(UiObject uiObject) throws DuplicateMySocioObjectException{
		String category = uiObject.getCategory();
		String name = uiObject.getName();
		if (getUiObject(category, name) != null){
			throw new DuplicateMySocioObjectException("UI object with name " + name + " already exists in category " + category);
		}
		saveObject(uiObject);
	}
	
	
	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IDataManager#saveObjects(java.util.List)
	 */
	public void saveObjects(List<? extends Object> objects) {
		for (Object object : objects) {
			saveObject(object);
		}
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IDataManager#saveObject(net.mysocio.data.ISocioObject)
	 */
	public void saveObject(Object object) {
		Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            pm.makePersistent(object);
            tx.commit();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
	}
	
	public void deleteObject(Object object) {
		Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            pm.deletePersistent(object);
            tx.commit();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
	}
	
	public<T> T createUniqueObject(Class T, String query, T object){
		Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            Extent<T> extent = pm.getExtent (T, true);
            Query q=pm.newQuery(extent, query);
            q.setUnique(true);
            T objectT = (T)q.execute();
            if (objectT == null){
            	objectT = pm.makePersistent(object);
            }else{
            	logger.info("Duplicate object of type: " + T.toString() + " for query: " + query);
            }
            tx.commit();
            return objectT;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IDataManager#saveSource(net.mysocio.connection.readers.ISource)
	 */
	public Source createSource(Source source, SocioUser user){
		Source createdSource = createUniqueObject(source.getClass(), getEqualsExpression("url", source.getUrl()), source);
		SocioObjectTags tags = new SocioObjectTags();
		tags.setId(getTagsId(createdSource, user));
		tags = createUniqueObject(tags.getClass(), getEqualsExpression("id", tags.getId()), tags);
		if (tags.getTags().isEmpty()){
			SocioTag tag = new SocioTag();
			tag.setOwnerId(user.getId());
			tag.setValue(createdSource.getName());
			tag = createTag(tag , user);
			tags.add(tag);
			saveObject(tags);
		}
		createdSource.setTags(tags);
		return createdSource;
	}
	
	public IMessage createMessage(IMessage message){
		IMessage createdMessage = createUniqueObject(message.getClass(), getEqualsExpression("uniqueId", message.getUniqueId()), message);
		return createdMessage;
	}


	/* (non-Javadoc)
	 * @see net.mysocio.data.management.IDataManager#saveContact(net.mysocio.data.Contact)
	 */
	public void createContact(Contact contact) {
		saveObject(contact);
	}

	public Map<String, IUiObject> getUserUiObjects(SocioUser user) {
		UserUiObjects objects;
		Query q=pm.newQuery(UserUiObjects.class, getEqualsExpression("userId", user.getId()));
		q.setUnique(true);
		objects = (UserUiObjects)q.execute();
		if (objects == null){
			return new HashMap<String, IUiObject>();
		}
		return objects.getUserUiObjects();
	}

	@Override
	public SocioTag createTag(SocioTag tag, SocioUser user) {
		return createUniqueObject(tag.getClass(), getEqualsExpression("value", tag.getValue()) + " && " + getEqualsExpression("ownerId", tag.getOwnerId()), tag);
	}
	
	private String getTagsId(ISocioObject object, SocioUser user){
		return user.getId() + object.getId();
	}
	private void retreiveTags(ISocioObject object, SocioUser user){
			SocioObjectTags tags = getUniqueObjectWithoutTags(SocioObjectTags.class, getEqualsExpression("id", getTagsId(object, user)));
	        object.setTags(tags);
	}
	private void retreiveTags(List<? extends ISocioObject> objects, SocioUser user){
		Map<String, ISocioObject> objectsMap = new HashMap<String, ISocioObject>();
		for (ISocioObject object : objects) {
			objectsMap.put(getTagsId(object, user),object);
		}
		Query q=pm.newQuery(SocioObjectTags.class);
		q.declareImports("import java.util.Collection");
		q.declareParameters("Collection objectsIds");
		q.setFilter("objectsIds.contains(id)");
		q.setOrdering("date ascending");
		List<SocioObjectTags> tagsList = (List<SocioObjectTags>)q.execute(objectsMap.keySet());
		for (SocioObjectTags socioObjectTags : tagsList) {
			ISocioObject object = objectsMap.get(socioObjectTags.getId());
			object.setTags(socioObjectTags);
		}
	}
	
	public List<IMessage> getMessages(List<String> ids){
		Query q=pm.newQuery(GeneralMessage.class);
		q.declareImports("import java.util.Collection");
		q.declareParameters("Collection objectsIds");
		q.setFilter("objectsIds.contains(id)");
		q.setOrdering("date ascending");
		return (List<IMessage>)q.execute(ids);
	}
}
