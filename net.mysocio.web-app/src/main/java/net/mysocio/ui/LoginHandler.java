/**
 * 
 */
package net.mysocio.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.authentication.UserIdentifier;
import net.mysocio.connection.readers.FacebookSource;
import net.mysocio.connection.readers.ISource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.FacebookAccount;
import net.mysocio.data.management.ConnectionData;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.JdoDataManager;
import net.mysocio.data.messages.FacebookMessage;
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
		JdoDataManager dataManager = (JdoDataManager)DataManagerFactory.getDataManager();
		SocioUser user = dataManager.getUser(account, new Locale("ru"));
		FacebookSource fs = new FacebookSource();
		fs.setName("Test Facebook account");
		fs.setUrl("testSourceId");
		fs = (FacebookSource)dataManager.createSource(fs, user);
		user.addSource(fs);
		FacebookSource fs1 = new FacebookSource();
		fs1.setName("Test Facebook account1");
		fs1.setUrl("testSourceId1");
		fs1 = (FacebookSource)dataManager.createSource(fs1, user);
		user.addSource(fs1);
		dataManager.createMessage(createLongTestMessage(fs1));
		dataManager.createMessage(createShortTestMesssage(1, fs1));
		dataManager.createMessage(createShortTestMesssage(2, fs1));
		dataManager.createMessage(createShortTestMesssage(3, fs1));
		dataManager.createMessage(createShortTestMesssage(4, fs1));
		dataManager.createMessage(createShortTestMesssage(5, fs1));
		dataManager.createMessage(createShortTestMesssage(6, fs1));
		dataManager.createMessage(createShortTestMesssage(7, fs1));
		dataManager.createMessage(createShortTestMesssage(8, fs1));
		dataManager.createMessage(createShortTestMesssage(9, fs1));
		dataManager.createMessage(createShortTestMesssage(10, fs1));
		dataManager.updateUnreaddenMessages(user);
		connectionData.setUser(user);
		return "pages/closingWindow.html";
	}

	/**
	 * @return
	 */
	private static FacebookMessage createLongTestMessage(FacebookSource fs1) {
		FacebookMessage message = new FacebookMessage();
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title");
		message.setSourceId(fs1.getId());
		message.setText(DefaultResourcesManager.getResource(new Locale("ru"), "mesage.test.long"));
		message.setLink("http://aladdill.livejournal.com/207656.html");
		return message;
	}

	/**
	 * @param i 
	 * @return
	 */
	private static FacebookMessage createShortTestMesssage(int i, ISource source) {
		FacebookMessage message = new FacebookMessage();
		message.setLink(source.getUrl() + "Test message Title"  + i);
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title" + i);
		message.setSourceId(source.getId());
		message.setText(DefaultResourcesManager.getResource(new Locale("ru"), "mesage.test.short"));
		return message;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
