/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class AuthenticationDoneExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationDoneExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		String identifier = connectionData.getRequestParameter(AccountsManager.IDENTIFIER);
		connectionData.setSessionAttribute(AccountsManager.IDENTIFIER, identifier);
		logger.debug("Done authenticating acount of type:" + identifier);
		String error = connectionData.getRequestParameter("error");
		if (error != null && error.equals("access_denied")){
			logger.error("Access to user account wasn't granted.");
			throw new CommandExecutionException("Are you sure you want to log in?");
		}
		String code = connectionData.getRequestParameter("code");
		connectionData.setSessionAttribute("code", code);
		return DefaultResourcesManager.getPage("closingLoginWindow.html");
	}
}
