/**
 * 
 */
package net.mysocio.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.mysocio.authentication.AuthenticationResourcesManager;
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
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.management.MongoDataManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

/**
 * @author Aladdin
 * 
 */
public class MySocioContextListener implements ServletContextListener {
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
			String dbServer = AuthenticationResourcesManager.getResource("db.server.address");
			int dbPort = Integer.parseInt(AuthenticationResourcesManager.getResource("db.server.port"));
			String dbName = AuthenticationResourcesManager.getResource("db.server.db.name");
			String dbUser = AuthenticationResourcesManager.getResource("db.server.admin.username");
			String dbPass = AuthenticationResourcesManager.getResource("db.server.admin.password");
			Datastore ds = new Morphia().createDatastore(new Mongo(
					dbServer,
					dbPort),
					dbName,
					dbUser,
					dbPass.toCharArray());
			ds.ensureCaps();
			ds.ensureIndexes();
			IDataManager manager = new MongoDataManager(ds);
			DataManagerFactory.init(manager);
			MongoURI uri = new MongoURI("mongodb://" + dbServer + ":" + dbPort);
			Mongo connectionBean = new Mongo(uri);
			DB db = connectionBean.getDB(dbName);
			db.authenticate(dbUser, dbPass.toCharArray());
			if (!db.collectionExists("route_packages")){
				DBObject options = BasicDBObjectBuilder.start().add("capped", true).add("size", 10000000).get();
				db.createCollection("route_packages", options);
			}
			if (!db.collectionExists("temp_routes")){
				DBObject options = BasicDBObjectBuilder.start().add("capped", true).add("size", 10000000).get();
				db.createCollection("temp_routes", options);
			}
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
		AccountsManager.getInstance().addAccount("vkontakte",
				new VkontakteAuthenticationManager());
		AccountsManager.getInstance().addAccount("linkedin",
				new LinkedinAuthenticationManager());
		AccountsManager.getInstance().addAccount(LjAccount.ACCOUNT_TYPE,
				new LjAuthenticationManager());
	}
}
