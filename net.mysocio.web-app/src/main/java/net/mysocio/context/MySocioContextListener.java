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
import net.mysocio.authentication.test.TestAuthenticationManager;
import net.mysocio.authentication.twitter.TwitterAuthenticationManager;
import net.mysocio.authentication.vkontakte.VkontakteAuthenticationManager;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.management.JdoDataManager;

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
		JdoDataManager.closeDataConnection();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();
		DefaultResourcesManager.init(servletContext.getRealPath(""));
		AuthenticationResourcesManager.init(servletContext.getRealPath(""));
		System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", "web-proxy.isr.hp.com");
        System.getProperties().put("proxyPort", "8080");
        CamelContextManager.initContext();
        AccountsManager.getInstance().addAccount("google", new GoogleAuthenticationManager());
        AccountsManager.getInstance().addAccount("facebook", new FacebookAuthenticationManager());
        AccountsManager.getInstance().addAccount("twitter", new TwitterAuthenticationManager());
        AccountsManager.getInstance().addAccount("vkontakte", new VkontakteAuthenticationManager());
        AccountsManager.getInstance().addAccount("linkedin", new LinkedinAuthenticationManager());
        AccountsManager.getInstance().addAccount("test", new TestAuthenticationManager());
	}
}
