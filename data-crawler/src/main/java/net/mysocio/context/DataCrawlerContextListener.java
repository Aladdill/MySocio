/**
 * 
 */
package net.mysocio.context;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.authentication.google.GoogleAuthenticationManager;
import net.mysocio.authentication.linkedin.LinkedinAuthenticationManager;
import net.mysocio.authentication.lj.LjAuthenticationManager;
import net.mysocio.authentication.twitter.TwitterAuthenticationManager;
import net.mysocio.authentication.vkontakte.VkontakteAuthenticationManager;
import net.mysocio.camel.CamelContextManager;
import net.mysocio.data.IDataManager;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.accounts.google.GoogleAccount;
import net.mysocio.data.accounts.lj.LjAccount;
import net.mysocio.data.accounts.vkontakte.VkontakteAccount;
import net.mysocio.data.management.AbstractMongoInitializer;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.AuthenticationResourcesManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.MongoDataManager;
import net.mysocio.data.management.camel.DefaultUserProcessor;
import net.mysocio.data.management.camel.MarkMessageReaddenProcessor;
import net.mysocio.data.management.camel.RSSFeedsProcessor;
import net.mysocio.routes.reader.MongoCappedCollectionReader;
import net.mysocio.routes.reader.NewPackageProcessor;
import net.mysocio.routes.reader.NewUserProcessor;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.mongodb.DB;

/**
 * @author Aladdin
 * 
 */
public class DataCrawlerContextListener extends AbstractMongoInitializer implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(DataCrawlerContextListener.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		CamelContextManager.stopContext();
		DataManagerFactory.closeDataConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		System.setProperty("file.encoding", "UTF-8");
		MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
		Morphia morphia = new Morphia();
		morphia.mapPackage("net.mysocio.data")
				.mapPackage("net.mysocio.ui.data.objects")
				.mapPackage("net.mysocio.connection");
		ServletContext servletContext = arg0.getServletContext();
		AuthenticationResourcesManager.init(servletContext.getRealPath(""));
		
		try {
			Datastore ds = getMongoDatastore(DB_SERVER_ADMIN_USERNAME, DB_SERVER_DB_NAME, DB_SERVER_PORT,
					DB_SERVER_ADDRESS, DB_SERVER_ADMIN_PASSWORD);
			Datastore processorsDs = getMongoDatastore(PROCESSORS_DB_SERVER_ADMIN_USERNAME,
					PROCESSORS_DB_SERVER_DB_NAME, PROCESSORS_DB_SERVER_PORT,
					PROCESSORS_DB_SERVER_ADDRESS,	PROCESSORS_DB_SERVER_ADMIN_PASSWORD);
			IDataManager manager = new MongoDataManager(ds, processorsDs);
			DataManagerFactory.init(manager);
			DB db = getMongoDatabaseAndInitCappedCollections(DB_SERVER_ADMIN_USERNAME, DB_SERVER_DB_NAME, DB_SERVER_PORT,
					DB_SERVER_ADDRESS, DB_SERVER_ADMIN_PASSWORD);
			final Thread routesThread = new Thread(new MongoCappedCollectionReader(db, TEMP_USER_PROCESSORS, new NewUserProcessor()));
			routesThread.start();
			final Thread packagesThread = new Thread(new MongoCappedCollectionReader(db, ROUTE_PACKAGES, new NewPackageProcessor()));
			packagesThread.start();
		} catch (Exception e) {
			logger.error("Error initializing mongo cursor.", e);
		}
		CamelContextManager.addComponent("activemq", ActiveMQComponent
				.activeMQComponent("vm://localhost?broker.persistent=true"));
		CamelContextManager.initContext();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<DefaultUserProcessor> processors = dataManager.getObjects(DefaultUserProcessor.class);
		logger.debug("Got " + processors.size() + " user processors.");
		try {
			MarkMessageReaddenProcessor readdenProcessor = new MarkMessageReaddenProcessor();
			CamelContextManager.addRoute(MarkMessageReaddenProcessor.ACTIVEMQ_READEN_MESSAGE, readdenProcessor);
			RSSFeedsProcessor rssFeedsProcessor = new RSSFeedsProcessor();
			CamelContextManager.addRoute(RSSFeedsProcessor.ACTIVEMQ_RSS_FEEDS, rssFeedsProcessor);
			for (DefaultUserProcessor processor : processors) {
				logger.debug("Starting processor route for user " + processor.getUserId());
				CamelContextManager.addRoute("timer://" + processor.getUserId() + "?fixedRate=true&period=60s", processor);
			}
		} catch (Exception e) {
			logger.error("Error initializing existing route", e);
		}
		AccountsManager.getInstance().addAccount(GoogleAccount.ACCOUNT_TYPE,
				new GoogleAuthenticationManager());
		AccountsManager.getInstance().addAccount(FacebookAccount.ACCOUNT_TYPE,
				new FacebookAuthenticationManager());
		AccountsManager.getInstance().addAccount("twitter",
				new TwitterAuthenticationManager());
		AccountsManager.getInstance().addAccount(VkontakteAccount.ACCOUNT_TYPE,
				new VkontakteAuthenticationManager());
		AccountsManager.getInstance().addAccount("linkedin",
				new LinkedinAuthenticationManager());
		AccountsManager.getInstance().addAccount(LjAccount.ACCOUNT_TYPE,
				new LjAuthenticationManager());
	}
}
