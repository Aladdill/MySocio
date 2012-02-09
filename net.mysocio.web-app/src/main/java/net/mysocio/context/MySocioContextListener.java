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
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.data.objects.AccountLine;
import net.mysocio.ui.data.objects.AddRssLine;
import net.mysocio.ui.data.objects.ContactLine;
import net.mysocio.ui.data.objects.DefaultAccountLine;
import net.mysocio.ui.data.objects.DefaultLoginPage;
import net.mysocio.ui.data.objects.DefaultMessage;
import net.mysocio.ui.data.objects.DefaultSiteBody;
import net.mysocio.ui.data.objects.NewAccountLine;
import net.mysocio.ui.data.objects.RssLine;
import net.mysocio.ui.data.objects.UserUiMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiMessage;

import org.apache.activemq.camel.component.ActiveMQComponent;

/**
 * @author Aladdin
 *
 */
public class MySocioContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		CamelContextManager.stopContext();
		DataManagerFactory.closeDataConnection();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		System.setProperty("file.encoding", "UTF-8");
//		System.getProperties().put("proxySet", "true");
//      System.getProperties().put("proxyHost", "web-proxy.isr.hp.com");
//      System.getProperties().put("proxyPort", "8080");
		DataManagerFactory.init("non-transactional");
		initUiObjects();
		ServletContext servletContext = arg0.getServletContext();
		DefaultResourcesManager.init(servletContext.getRealPath(""));
		AuthenticationResourcesManager.init(servletContext.getRealPath(""));
		CamelContextManager.addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=true"));
        CamelContextManager.initContext();
        AccountsManager.getInstance().addAccount(GoogleAccount.ACCOUNT_TYPE, new GoogleAuthenticationManager());
        AccountsManager.getInstance().addAccount(FacebookAccount.ACCOUNT_TYPE, new FacebookAuthenticationManager());
        AccountsManager.getInstance().addAccount("twitter", new TwitterAuthenticationManager());
        AccountsManager.getInstance().addAccount("vkontakte", new VkontakteAuthenticationManager());
        AccountsManager.getInstance().addAccount("linkedin", new LinkedinAuthenticationManager());
        AccountsManager.getInstance().addAccount(LjAccount.ACCOUNT_TYPE, new LjAuthenticationManager());
	}

	private void initUiObjects() {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.saveUiObject(new DefaultLoginPage());
		dataManager.saveUiObject(new NewAccountLine());
		dataManager.saveUiObject(new AccountLine());
		dataManager.saveUiObject(new DefaultAccountLine());
		dataManager.saveUiObject(new RssLine());
		dataManager.saveUiObject(new AddRssLine());
		dataManager.saveUiObject(new ContactLine());
		dataManager.saveUiObject(new DefaultMessage());
		dataManager.saveUiObject(new UserUiMessage());
		dataManager.saveUiObject(new FacebookUiMessage());
		dataManager.saveUiObject(new DefaultSiteBody());
	}
}
