/**
 * 
 */
package net.mysocio.ui;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.authentication.AuthenticationResourcesManager;
import net.mysocio.authentication.UserIdentifier;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.FacebookAccount;
import net.mysocio.data.management.ConnectionData;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.managers.basic.DefaultResourcesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Aladdin
 *
 */
public class LoginHandler extends AbstractHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6174410260385459501L;
	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	
	/**
	 * @param request
	 * @param responseString
	 * @return
	 * @throws CommandExecutionException
	 */
	protected String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws CommandExecutionException {
		IConnectionData connectionData = new ConnectionData(request);
		String responseString = "";
		String identifierString = connectionData.getRequestParameter("identifier");
		if ("test".equals(identifierString)){
			return handleTestRequest(connectionData);
		}
		if (identifierString != null){
			UserIdentifier identifier = UserIdentifier.valueOf(identifierString);
			if (identifier != null){
				logger.debug("Getting request url  for " + identifier.name());
				responseString = identifier.getAuthManager().getRequestUrl();
				request.getSession().setAttribute("identifier", identifier);
			}else{
				throw new  CommandExecutionException("No login medium was defined");
			}
		}
		String error = connectionData.getRequestParameter("error");
		if (error != null && error.equals("access_denied")){
			throw new CommandExecutionException("Are you sure you want to log in?");
		}
		String code = connectionData.getRequestParameter("code");
		if (code != null){
			UserIdentifier identifier = (UserIdentifier)request.getSession().getAttribute("identifier");
			try {
				Account account = identifier.getAuthManager().login(connectionData);
				SocioUser user = DataManagerFactory.getDataManager().getUser(account, connectionData.getLocale());
				connectionData.setUser(user);
			} catch (Exception e) {
				throw new CommandExecutionException(e);
			}
			request.getSession().removeAttribute("identifier");
			responseString = DefaultResourcesManager.getPage("closingWindow.html");
		}
		return responseString;
	}

	private String handleTestRequest(IConnectionData connectionData) {
		Account account = new FacebookAccount();
		account.setAccountUniqueId("test@test.com");
		account.setUserName("Vasya Pupkin");
		account.setUserpicUrl("images/portrait.jpg");
		SocioUser user = DataManagerFactory.getDataManager().getUser(account, connectionData.getLocale());
		connectionData.setUser(user);
		return "pages/closingWindow.html";
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
