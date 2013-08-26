/**
 * 
 */
package net.mysocio.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.authentication.google.GoogleAuthenticationManager;
import net.mysocio.authentication.linkedin.LinkedinAuthenticationManager;
import net.mysocio.authentication.lj.LjAuthenticationManager;
import net.mysocio.authentication.twitter.TwitterAuthenticationManager;
import net.mysocio.authentication.vkontakte.VkontakteAuthenticationManager;
import net.mysocio.data.IDataManager;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.accounts.google.GoogleAccount;
import net.mysocio.data.accounts.lj.LjAccount;
import net.mysocio.data.accounts.vkontakte.VkontakteAccount;
import net.mysocio.data.management.AbstractMongoInitializer;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.AuthenticationResourcesManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.management.MongoDataManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;

/**
 * @author Aladdin
 * 
 */
public class MySocioContextListener extends AbstractMongoInitializer implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(MySocioContextListener.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
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
		Morphia morphia = new Morphia();
		morphia.mapPackage("net.mysocio.data")
				.mapPackage("net.mysocio.ui.data.objects")
				.mapPackage("net.mysocio.connection");
		ServletContext servletContext = arg0.getServletContext();
		DefaultResourcesManager.init(servletContext.getRealPath(""));
		AuthenticationResourcesManager.init(servletContext.getRealPath(""));
		try {
			Datastore ds = getMongoDatastore(DB_SERVER_ADMIN_USERNAME, DB_SERVER_DB_NAME, DB_SERVER_PORT,
					DB_SERVER_ADDRESS, DB_SERVER_ADMIN_PASSWORD);
			Datastore processorsDs = getMongoDatastore(PROCESSORS_DB_SERVER_ADMIN_USERNAME,
					PROCESSORS_DB_SERVER_DB_NAME, PROCESSORS_DB_SERVER_PORT,
					PROCESSORS_DB_SERVER_ADDRESS,	PROCESSORS_DB_SERVER_ADMIN_PASSWORD);
			IDataManager manager = new MongoDataManager(ds, processorsDs);
			DataManagerFactory.init(manager);
			getMongoDatabaseAndInitCappedCollections(DB_SERVER_ADMIN_USERNAME, DB_SERVER_DB_NAME, DB_SERVER_PORT,
					DB_SERVER_ADDRESS, DB_SERVER_ADMIN_PASSWORD);
		} catch (Exception e) {
			logger.error("Error initializing database", e);
		}
		DefaultUiManager.init();
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
