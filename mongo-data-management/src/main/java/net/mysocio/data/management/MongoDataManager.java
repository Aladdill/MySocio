/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.CappedCollectionTimeStamp;
import net.mysocio.data.IDataManager;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.IUniqueObject;
import net.mysocio.data.RoutePackage;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioRoute;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.TempRoute;
import net.mysocio.data.UserAccount;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.camel.DefaultUserMessagesProcessor;
import net.mysocio.data.management.exceptions.DuplicateMySocioObjectException;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.ReaddenMessage;
import net.mysocio.data.messages.UnreaddenMessage;
import net.mysocio.data.ui.UiObject;
import net.mysocio.data.ui.UserPage;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;

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
		saveObject(user);
		userId = user.getId().toString();
		account.setUser(user);
		saveObject(account);
		user.setMainAccount(account);
		saveObject(user);
		UserTags userTags = new UserTags();
		userTags.setUserId(user.getId().toString());
		addAccountToUser(account, userId, userTags);
		DefaultUserMessagesProcessor processor = new DefaultUserMessagesProcessor();
		processor.setUserId(userId);
		createRoute("activemq:" + userId + ".newMessage", processor, 0l);
		logger.debug("User created");
		return user;
	}

	public void createRoute(String from, AbstractProcessor processor, Long delay)
			throws DuplicateMySocioObjectException {
		TempRoute route = new TempRoute();
		route.setFrom(from);
		route.setProcessor(processor);
		route.setDelay(delay);
		route.setCreationDate(System.currentTimeMillis());
		saveObject(route);
	}
	@Override
	public void removeRoute(String from, String userId){
		Query<SocioRoute> q = ds.createQuery(SocioRoute.class).field("from").equal(from).field("processor.to").equal("activemq:" + userId + ".newMessage");
		ds.delete(q);
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
		source.createRoute("activemq:" + userId  + ".newMessage");
	}
	
	@Override
	public boolean isNewMessage(String userId, GeneralMessage message){
		Query<UnreaddenMessage>  isUnread = ds.createQuery(UnreaddenMessage.class).field("userId").equal(userId).field("message").equal(message);
		Query<ReaddenMessage>  isRead = ds.createQuery(ReaddenMessage.class).field("userId").equal(userId).field("messageUniqueId").equal(message.getUniqueFieldValue().toString());
		return (isUnread.countAll() <= 0 && isRead.countAll() <= 0);
	}
	
	/**
	 * @param account
	 * @return
	 */
	public Account getAccount(String accountUniqueId) {
		Query<Account> q = ds.createQuery(Account.class).field("accountUniqueId").equal(accountUniqueId);
		return q.get();
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
		return ds.find(T).asList();
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
			Query<T> q = (Query<T>)ds.createQuery(object.getClass()).field(uniqueObject.getUniqueFieldName()).equal(uniqueObject.getUniqueFieldValue());
			T objectT = (T) q.get();
			if (objectT != null) {
				((SocioObject)object).setId(objectT.getId());
				logger.info("Duplicate object of type: " + object.getClass() + " for query: " + q.toString());
				throw new DuplicateMySocioObjectException("Duplicate object of type: " + object.getClass() + " for query: " + q.toString());
			}
		}
		ds.save(object);
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

	public List<UnreaddenMessage> getUnreadMessages(String userId, String tagId, UserTags tags) {
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
		List<UnreaddenMessage> messagesList = q.asList();
		tags.setSelectedTag(tagId);
		ds.save(tags);
		return messagesList;
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
	public boolean isRouteExist(String from, AbstractProcessor processor) {
		Query<SocioRoute>  routes = ds.createQuery(SocioRoute.class).field("from").equal(from);
		if (processor != null){
			routes.field("processor.to").equal(processor.getTo());
		}
		return (routes.countAll() > 0);
	}
}
