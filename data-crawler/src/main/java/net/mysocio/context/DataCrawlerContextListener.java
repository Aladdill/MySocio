/**
 * 
 */
package net.mysocio.context;

import java.util.List;

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
import net.mysocio.camel.CamelContextManager;
import net.mysocio.camel.NewPackageProcessor;
import net.mysocio.camel.NewRouteProcessor;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioRoute;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.accounts.google.GoogleAccount;
import net.mysocio.data.accounts.lj.LjAccount;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.MongoDataManager;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.util.jndi.JndiContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

/**
 * @author Aladdin
 * 
 */
public class DataCrawlerContextListener implements ServletContextListener {
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
		Morphia morphia = new Morphia();
		morphia.mapPackage("net.mysocio.data")
				.mapPackage("net.mysocio.ui.data.objects")
				.mapPackage("net.mysocio.connection");
		ServletContext servletContext = arg0.getServletContext();
		AuthenticationResourcesManager.init(servletContext.getRealPath(""));
		String dbUser = AuthenticationResourcesManager.getResource("db.server.admin.username");
		String dbName = AuthenticationResourcesManager.getResource("db.server.db.name");
		int dbPort = Integer.parseInt(AuthenticationResourcesManager.getResource("db.server.port"));
		String dbServer = AuthenticationResourcesManager.getResource("db.server.address");
		String dbPass = AuthenticationResourcesManager.getResource("db.server.admin.password");
		try {
			Datastore ds = new Morphia().createDatastore(new Mongo(
					dbServer,
					dbPort),
					dbName,
					dbUser,
					dbPass.toCharArray());
			ds.ensureCaps();
			IDataManager manager = new MongoDataManager(ds);
			DataManagerFactory.init(manager);
		} catch (Exception e) {
			logger.error("Error initializing database", e);
		}
		CamelContextManager.addComponent("activemq", ActiveMQComponent
				.activeMQComponent("vm://localhost?broker.persistent=true"));
		try {
			JndiContext context = new JndiContext();
			Mongo connectionBean = null;

			MongoURI uri = new MongoURI("mongodb://" + dbServer + ":" + 10044);
			connectionBean = new Mongo(uri);
			DB connectDB = connectionBean.getDB(dbName);
			connectDB.authenticate(dbUser, dbPass.toCharArray());
			context.bind("myConnectionBean", connectionBean);
			CamelContextManager.initContext(context);
		} catch (Exception e) {
			logger.error("Error initializing JNDI context", e);
		}
		List<SocioRoute> routes = DataManagerFactory.getDataManager().getObjects(SocioRoute.class);
		try {
			for (SocioRoute route : routes) {
				CamelContextManager.addRoute(route);
			}
		} catch (Exception e) {
			logger.error("Error initializing existing route", e);
		}
		try {
			SocioRoute routesRoute = new SocioRoute();
			routesRoute.setFrom("mongodb:myConnectionBean?database="+dbName+"&collection=temp_routes&tailTrackIncreasingField=creationDate&persistentTailTracking=true" +
					"&persistentId=routesTracker&tailTrackField=lastRouteTrackers");
			routesRoute.setAutoStartup(false);
			routesRoute.setProcessor(new NewRouteProcessor());
			CamelContextManager.addRoute(routesRoute);
		} catch (Exception e) {
			logger.error("Error initializing routes route", e);
		}
		
		try {
			SocioRoute packagesRoute = new SocioRoute();
			packagesRoute.setFrom("mongodb:myConnectionBean?database="+dbName+"&collection=route_packages&tailTrackIncreasingField=creationDate&persistentTailTracking=true" +
					"&persistentId=packageTracker&tailTrackField=lastPackageTracker");
			packagesRoute.setAutoStartup(false);
			packagesRoute.setProcessor(new NewPackageProcessor());
			CamelContextManager.addRoute(packagesRoute);
		} catch (Exception e) {
			logger.error("Error initializing routes route", e);
		}
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
