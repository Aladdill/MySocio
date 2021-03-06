/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.AbstractUserMessagesProcessor;
import net.mysocio.data.CappedCollectionTimeStamp;
import net.mysocio.data.IDataManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.IUniqueObject;
import net.mysocio.data.RoutePackage;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.TempProcessor;
import net.mysocio.data.UserAccount;
import net.mysocio.data.UserPermissions;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.camel.DefaultUserProcessor;
import net.mysocio.data.management.exceptions.DuplicateMySocioObjectException;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.ReaddenMessage;
import net.mysocio.data.messages.UnreaddenMessage;
import net.mysocio.data.ui.UiObject;
import net.mysocio.data.ui.UserPage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;

/**
 * @author Aladdin
 * 
 */
public class MongoDataManager implements IDataManager {
	private static final Logger logger = LoggerFactory.getLogger(MongoDataManager.class);
	private Datastore ds;
	private Datastore processorsDs;
	private CacheManager cm;
	

	public MongoDataManager(Datastore ds, Datastore processorsDs) {
		this.ds = ds;
		this.processorsDs = processorsDs;
		this.cm = CacheManager.create();
	}

	public SocioUser createUser(Account account, Locale locale)
			throws Exception {
		String userId;
		String userName = account.getUserName();
		logger.debug("Creating user"  + userName + " for "	+ account.getAccountType());
		SocioUser user = new SocioUser();
		user.setName(userName);
		user.setLocale(locale.getLanguage());
		saveObject(user);
		userId = user.getId().toString();
		account.setUser(user);
		saveObject(account);
		user.setMainAccount(account);
		saveObject(user);
		UserTags userTags = new UserTags();
		userTags.setUserId(user.getId().toString());
		addAccountToUser(account, userId, userTags);
		TempProcessor processor = new TempProcessor();
		processor.setUserId(userId);
		processor.setCreationDate(System.currentTimeMillis());
		saveObject(processor);
		logger.debug("User created");
		return user;
	}

	@Override
	public<T> void deleteProcessorByField(Class<T> clazz, String fieldName, String fieldValue){
		Query<T> q = processorsDs.createQuery(clazz).field(fieldName).equal(fieldValue);
		processorsDs.delete(q);
	}
	
	@Override
	public<T> void deleteUserProcessorByField(Class<T> clazz, String fieldName, String fieldValue, String userId){
		Query<T> q = processorsDs.createQuery(clazz).field(fieldName).equal(fieldValue).field("userId").equal(userId);
		processorsDs.delete(q);
	}
	
	@Override
	public Source getSource(String url){
		Query<Source> q = ds.createQuery(Source.class).field("url").equal(url);
		return q.get();
	}
	
	public void sendPackageToRoute(String to, SocioObject object) throws DuplicateMySocioObjectException{
		RoutePackage routePackage = new RoutePackage();
		routePackage.setTo(to);
		routePackage.setObject(object);
		routePackage.setCreationDate(System.currentTimeMillis());
		saveObject(routePackage);
	}

	/**
	 * @param account
	 * @param user
	 * @throws Exception 
	 */
	public void addAccountToUser(Account account, String userId, UserTags userTags) throws Exception {
		Query<UserAccount> q = ds.createQuery(UserAccount.class).field("account").equal(account);
		if(q.get() != null){
			logger.debug("Attempt was made to add existing account of type " + account.getAccountType() + " to user " + userId);
			return;
		}
		SocioUser user = getObject(SocioUser.class, userId);
		account.setUser(user);
		saveObject(account);
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(userId);
		userAccount.setAccount(account);
		saveObject(userAccount);
		account.createAccountTagset(userTags);
		saveObject(userTags);
		for (Source source : account.getSources()) {
			addSourceToUser(userId, source);
		}
	}
	
	public void addSourceToUser(String userId, Source source) throws Exception{
		try {
			saveObject(source);
		} catch (DuplicateMySocioObjectException e) {
			//Cool!!! we already have this source no need to save it, let's see if user already has it.
			UserTags userTags = getUserTags(userId);
			if (userTags.getTag(source.getUrl()) != null){
				logger.debug("User trying to add existing source.");
				return;
			}
		}
		source.createProcessor(userId);
	}
	
	@Override
	public<T extends GeneralMessage> boolean isNewMessage(String userId, T message, Class<T> T){
		Cache cache = cm.getCache("Messages");
		String key = message.getUniqueFieldValue() + userId;
		Element element = cache.get(key);
		if (element != null){
			return (Boolean)element.getValue();
		}
		Query<UnreaddenMessage>  isUnread = ds.createQuery(UnreaddenMessage.class).field("userId").equal(userId).field("message").equal(new Key<T>(T, message.getId()));
		Query<ReaddenMessage>  isRead = ds.createQuery(ReaddenMessage.class).field("userId").equal(userId).field("messageUniqueId").equal(message.getUniqueFieldValue().toString());
		Boolean newMessage = isUnread.countAll() <= 0 && isRead.countAll() <= 0;
		element = new Element(key, new Boolean(false));
		cache.put(element);
		return newMessage;
	}
	
	/**
	 * @param account
	 * @return
	 */
	public Account getAccount(String accountUniqueId) {
		Query<Account> q = ds.createQuery(Account.class).field("accountUniqueId").equal(accountUniqueId);
		return q.get();
	}
	
	@Override
	public UserPermissions getUserPermissions(String mail) {
		Query<UserPermissions> q = ds.createQuery(UserPermissions.class).field("mail").equal(mail);
		return q.get();
	}
	
	public List<AbstractUserMessagesProcessor> getUserProcessors(String userId) {
		Query<AbstractUserMessagesProcessor> q = processorsDs.createQuery(AbstractUserMessagesProcessor.class).field("userId").equal(userId);
		return q.asList();
	}

	public<T> T getObject(Class<T> T, String id) {
		Query<T>  q = (Query<T>)ds.createQuery(T).field("id").equal(new ObjectId(id));
		return q.get();
	}
	
	public CappedCollectionTimeStamp getTimestamp(String collection) {
		Query<CappedCollectionTimeStamp>  q = (Query<CappedCollectionTimeStamp>)ds.createQuery(CappedCollectionTimeStamp.class).field("collection").equal(collection);
		return q.get();
	}

	public UiObject getUiObject(String category, String name) {
		Query<UiObject>  q = ds.createQuery(UiObject.class).field("category").equal(category)
				.field("name").equal(name);
		return q.get();
	}

	public <T extends ISocioObject> List<T> getObjects(Class<T> T) {
		Query<T> q = (Query<T>)ds.createQuery(T);
		Iterable<T> objects = q.fetch();
		ArrayList<T> ret = new ArrayList<T>();
		for (T object : objects) {
			ret.add(object);
		}
		return ret;
	}

	public void saveUiObject(UiObject uiObject) throws DuplicateMySocioObjectException{
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
	public <T extends ISocioObject> void saveObjects(Class<T> T, List<T> objects) {
		for (T object : objects) {
			try {
				saveObject(object);
			} catch (DuplicateMySocioObjectException e) {
				logger.info("Duplicate object was found");
			}
		}
	}

	public void deleteObject(Object object) {
		ds.delete(object);
	}

	public <T extends ISocioObject> void saveObject(T object) throws DuplicateMySocioObjectException {
		if (object instanceof IUniqueObject){
			IUniqueObject uniqueObject = (IUniqueObject)object;
			Cache cache = cm.getCache("Objects");
			String key =uniqueObject.getUniqueFieldName() + uniqueObject.getUniqueFieldValue();
			Element element = cache.get(key);
			if (element != null){
				((SocioObject)object).setId((ObjectId)element.getValue());
				return;
			}
			@SuppressWarnings("unchecked")
			Query<T> q = (Query<T>)ds.createQuery(object.getClass()).field(uniqueObject.getUniqueFieldName()).equal(uniqueObject.getUniqueFieldValue());
			T objectT = (T) q.get();
			if (objectT != null) {
				((SocioObject)object).setId(objectT.getId());
				element = new Element(key, objectT.getId());
				cache.put(element);
				logger.info("Duplicate object of type: " + object.getClass() + " for query: " + q.toString());
				throw new DuplicateMySocioObjectException("Duplicate object of type: " + object.getClass() + " for query: " + q.toString());
			}
		}
		ds.save(object);
	}
	
	@Override
	public <T extends ISocioObject> void saveExistingObject(T object) {
		ds.save(object);
	}
	
	@Override
	public<T extends AbstractUserMessagesProcessor> void saveProcessor(T processor, String uniqueFieldName, String uniqueFieldValue) throws DuplicateMySocioObjectException {
		@SuppressWarnings("unchecked")
		Query<T> q = (Query<T>)processorsDs.createQuery(processor.getClass()).field(uniqueFieldName).equal(uniqueFieldValue);
		String userId = processor.getUserId();
		if (userId != null){
			q.field("userId").equal(userId);
		}
		AbstractUserMessagesProcessor existingProcessor = q.get();
		if (existingProcessor != null) {
			processor.setId(existingProcessor.getId());
			logger.info("Duplicate processor for query: " + q.toString());
			throw new DuplicateMySocioObjectException("Duplicate processor for query: " + q.toString());
		}
		processorsDs.save(processor);
	}
	
	@Override
	public void saveProcessor(AbstractUserMessagesProcessor processor) {
		processorsDs.save(processor);
	}
	
	@Override
	public void setMessageReadden(String userId, String messageId) {
		Query<UnreaddenMessage> q = ds.createQuery(UnreaddenMessage.class).field("message").equal(new Key<GeneralMessage>(GeneralMessage.class, new ObjectId(messageId))).field("userId").equal(userId);
		ds.delete(q);
		ReaddenMessage readdenMessage = new ReaddenMessage();
		readdenMessage.setUserId(userId);
		readdenMessage.setMessageUniqueId(ds.get(GeneralMessage.class, new ObjectId(messageId)).getUniqueFieldValue().toString());
		ds.save(readdenMessage);
		//TODO Next lines are just to save space in unpaid mongoDB on CloudBees  
		Query<UnreaddenMessage> isMore = ds.createQuery(UnreaddenMessage.class).field("message").equal(new Key<GeneralMessage>(GeneralMessage.class, new ObjectId(messageId)));
		if (isMore.countAll() <= 0){
			ds.delete(GeneralMessage.class, new ObjectId(messageId));
		}
	}
	
	@Override
	public void setMessagesReadden(String userId, String tagId, UserTags tags) {
		Query<UnreaddenMessage> q = ds.createQuery(UnreaddenMessage.class).field("userId").equal(userId);
		if (!tagId.equals(UserTags.ALL_TAGS)){
			SocioTag tag = tags.getTag(tagId);
			List<String> tagsIds = new ArrayList<String>();
			List<SocioTag> leaves = tag.getLeaves();
			for (SocioTag leaf : leaves) {
				tagsIds.add(leaf.getUniqueId());
			}
			q.field("tagId").hasAnyOf(tagsIds);
		}
		List<UnreaddenMessage> unreadMessages = q.asList();
		List<GeneralMessage> messages = new ArrayList<GeneralMessage>();
		List<ReaddenMessage> readdenMessages = new ArrayList<ReaddenMessage>();
		for (UnreaddenMessage unreaddenMessage : unreadMessages) {
			GeneralMessage message = unreaddenMessage.getMessage();
			messages.add(message);
			ReaddenMessage readdenMessage = new ReaddenMessage();
			readdenMessage.setUserId(userId);
			readdenMessage.setMessageUniqueId(message.getUniqueFieldValue().toString());
			readdenMessages.add(readdenMessage);
		}
		ds.save(readdenMessages);
		ds.delete(q);
		for (GeneralMessage message : messages) {
			//TODO Next lines are just to save space in unpaid mongoDB on CloudBees  
			Query<UnreaddenMessage> isMore = ds.createQuery(UnreaddenMessage.class).field("message").equal(message);
			if (isMore.countAll() <= 0){
				ds.delete(message);
			}
		}
	}

	public List<GeneralMessage> getUnreadMessages(String userId, String tagId, UserTags tags) {
		
		Query<UnreaddenMessage> q = ds.createQuery(UnreaddenMessage.class).field("userId").equal(userId.toString());
		String order = tags.getOrder();
		if (!tagId.equals(UserTags.ALL_TAGS)){
			SocioTag tag = tags.getTag(tagId);
			order = tag.getOrder();
			List<String> tagsIds = new ArrayList<String>();
			List<SocioTag> leaves = tag.getLeaves();
			for (SocioTag leaf : leaves) {
				tagsIds.add(leaf.getUniqueId());
			}
			q.field("tagId").hasAnyOf(tagsIds);
		}
		if (order.equals(SocioTag.ASCENDING_ORDER)){
			q.order("-messageDate");
		}else{
			q.order("messageDate");
		}
		q.limit(tags.getRange());
		List<GeneralMessage> messagesList = new ArrayList<GeneralMessage>();
		Iterable<UnreaddenMessage> messages = q.fetch();
		for (UnreaddenMessage unreaddenMessage : messages) {
			messagesList.add(unreaddenMessage.getMessage());
		}
		tags.setSelectedTag(tagId);
		ds.save(tags);
		return messagesList;
	}
	
	@Override
	public<T extends GeneralMessage> List<T> getMessagesAfterDate(Class<T> T, Long date, String sourceField, String sourceId){
		Query<T> q = (Query<T>)ds.createQuery(T).field("date").greaterThanOrEq(date).field(sourceField).equal(sourceId);
		Iterable<T> messages = q.fetch();
		ArrayList<T> ret = new ArrayList<T>();
		for (T message : messages) {
			ret.add(message);
		}
		return ret;
	}

	public Long countUnreadMessages(String userId, String tagId) {
		return ds.createQuery(UnreaddenMessage.class).field("tagId").equal(tagId).field("userId").equal(userId).countAll();
	}

	public String getPage(String userId, String pageKey) {
		Query<UserPage> q = ds.createQuery(UserPage.class).field("userId").equal(userId).field("pageKey").equal(pageKey);
		UserPage userPage = q.get();
		if (userPage == null){
			return null;
		}
		return userPage.getPageHTML();
	}

	public UserTags getUserTags(String userId) {
		Query<UserTags> q = ds.createQuery(UserTags.class).field("userId").equal(userId);
		return q.get();
	}


	public List<UserAccount> getAccounts(String userId) {
		Query<UserAccount> q = ds.createQuery(UserAccount.class).field("userId").equal(userId);
		return q.asList();
	}

	@Override
	public boolean isProcessorExist(String userId) {
		Query<DefaultUserProcessor>  processor = processorsDs.createQuery(DefaultUserProcessor.class).field("userId").equal(userId);
		return (processor.countAll() > 0);
	}
}
