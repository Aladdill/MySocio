package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.ISourcesGroup;
import net.mysocio.connection.readers.Source;
import net.mysocio.connection.readers.SourcesGroup;
import net.mysocio.data.Contact;
import net.mysocio.data.GeneralMessage;
import net.mysocio.data.IMessage;
import net.mysocio.data.ISocioObject;
import net.mysocio.data.MessagesIdComparator;
import net.mysocio.data.SocioUser;
import net.mysocio.data.UnreaddenMessages;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataManager {
	private static final Logger logger = LoggerFactory.getLogger(DataManager.class);
	private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); 
	public static SocioUser getUser(String identifier, String identifierValue) {
		if (logger.isDebugEnabled()){
			logger.debug("get user identifier " + identifier + " = " + identifierValue);
		}
		SocioUser socioUser;
		Session session = getSession();
		try{
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(SocioUser.class);
			criteria.add(Restrictions.eq(identifier, identifierValue));
			socioUser = (SocioUser)criteria.uniqueResult();
			session.persist(socioUser);
			transaction.commit();
		}finally{
			closeSession(session);
		}
		return socioUser;
	}
	public static List<IMessage> getMessages(ISource source, Long firstId) {
		List<IMessage> messages;
		Session session = getSession();
		try{
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(GeneralMessage.class);
			if (source instanceof ISourcesGroup){
				List<Long> ids = getIds(source);
				criteria.add(Restrictions.in("sourceId", ids));
			}else{
				criteria.add(Restrictions.eq("sourceId", source.getId()));
			}
			criteria.add(Restrictions.gt("id", firstId));
			messages = criteria.list();
			transaction.commit();
		}finally{
			closeSession(session);
		}
		return messages;
	}
	public static void addUnreadMessages(SocioUser user, Long sourceId, List<? extends IMessage> messages){
		if (messages.size() > 0){
			UnreaddenMessages unreaddenMessages = user.getUnreadMessages(sourceId);
			if (unreaddenMessages == null){
				unreaddenMessages = new UnreaddenMessages();
			}
			Collections.sort(messages, new MessagesIdComparator());
			unreaddenMessages.setLastId(messages.get(0).getId());
			unreaddenMessages.addMessages(messages);
			saveObject(unreaddenMessages);
			user.addUnreadMessages(sourceId, unreaddenMessages);
		}
	}
	
	private static List<Long> getIds(ISource source) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(source.getId());
		if (source instanceof ISourcesGroup) {
			ISourcesGroup group = (ISourcesGroup) source;
			List<ISource> sources = group.getSources();
			for (ISource child : sources) {
				ids.addAll(getIds(child));
			}
		}
		return ids;
	}

	public static ISource getSource(Long id){
		ISource source;
		Session session = getSession();
		try{
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Source.class);
			criteria.add(Restrictions.eq("id", id));
			source = (ISource)criteria.uniqueResult();
			session.persist(source);
			transaction.commit();
		}finally{
			closeSession(session);
		}
		return source;
	}
	
	public static List<ISource> getSources(Class clazz){
		List<ISource> sources;
		Session session = getSession();
		try{
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(clazz);
			sources = criteria.list();
			for (ISource source : sources) {
				session.persist(source);
			}
			transaction.commit();
		}finally{
			closeSession(session);
		}
		return sources;
	}
	
	private static Session getSession(){
		return sessionFactory.openSession();
	}

	public static void saveUser(SocioUser user){
		Session session = getSession();
		try{
			session.saveOrUpdate(user);
		}finally{
			closeSession(session);
		}
	}

	public static void saveContact(Contact contact) {
		Session session = getSession();
		try{
			saveContact(contact, session);
		}finally{
			closeSession(session);
		}
	}

	public static void saveContact(Contact contact, Session session) {
		saveSourcesGroup(contact.getSourcesGroup(), session);
		session.saveOrUpdate(contact);
	}
	
	public static void saveSourcesGroup(SourcesGroup sourcesGroup){
		Session session = getSession();
		try{
			saveSourcesGroup(sourcesGroup, session);
		}finally{
			closeSession(session);
		}
	}
	
	private static void saveSourcesGroup(SourcesGroup sourcesGroup,
			Session session) {
		for (ISource childSource : sourcesGroup.getSources()) {
			saveSource(childSource,session);
		}
	}
	
	public static void saveSource(ISource source){
		Session session = getSession();
		try{
			saveSource(source, session);
		}finally{
			closeSession(session);
		}
	}

	/**
	 * @param session
	 */
	private static void closeSession(Session session) {
		session.flush();
		session.close();
	}

	private static void saveSource(ISource source, Session session){
		session.saveOrUpdate(source);
	}

	public static void saveObject(ISocioObject object){
		Session session = getSession();
		try{
			session.saveOrUpdate(object);
		}finally{
			closeSession(session);
		}
	}
	public static void saveObjects(List<? extends ISocioObject> objects) {
		Session session = getSession();
		try{
			for (ISocioObject object : objects) {
				saveOneOfMany(object, session);
			}
		}catch (HibernateException e){e.printStackTrace();}
		finally{
			closeSession(session);
		}
	}
	private static void saveOneOfMany(ISocioObject object, Session session) {
		session.saveOrUpdate(object);
	}
	public static SocioUser createUser(String identifier, String identifierValue) {
		SocioUser user = new SocioUser();
		setUserIdentifier(user, identifier, identifierValue);
		saveUser(user);
		return user;
	}
	private static void setUserIdentifier(SocioUser user, String identifier,
			String identifierValue) {
		UserIdentifier userIdentifier = UserIdentifier.valueOf(identifier);
		switch (userIdentifier) {
		case email:
			user.setEmail(identifierValue);
			break;
		default:
			break;
		}
	}
}
