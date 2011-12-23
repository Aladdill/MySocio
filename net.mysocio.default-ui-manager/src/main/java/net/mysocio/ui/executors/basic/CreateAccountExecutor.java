/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
public class CreateAccountExecutor implements ICommandExecutor {
	private static final String CREATING_ACCOUNT_IN_LOGOUT = "Attempt to create account while in logged out state. Probable attempt of hacking.";
	private static final Logger logger = LoggerFactory.getLogger(CreateAccountExecutor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.
	 * IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		String flow = connectionData.getSessionAttribute("flow");
		logger.debug("Creating account in flow: " + flow);
		String responseString = null;
		try {
			SocioUser user = connectionData.getUser();
			Account account = AccountsManager.getInstance().getAccount(connectionData);
			IDataManager dataManager;
			if ("login".equals(flow)) {
				dataManager = DataManagerFactory.getDataManager();
				user = dataManager.getUser(account,connectionData.getLocale());
				responseString = DefaultResourcesManager.getPage("closingLoginWindow.html");
			} else if ("addAccount".equals(flow)) {
				if (user == null) {
					logger.error(CREATING_ACCOUNT_IN_LOGOUT);
					throw new CommandExecutionException(
							CREATING_ACCOUNT_IN_LOGOUT);
				}
				dataManager = DataManagerFactory.getDataManager(user);
				dataManager.addAccountToUser(account, user);
				responseString = DefaultResourcesManager.getPage("closingAddAccountWindow.html");
			} else {
				logger.error("Unknown flow:" + flow);
				throw new CommandExecutionException("Unknown flow:" + flow);
			}
			connectionData.setUser(user);
		} catch (Exception e) {
			logger.error("Failed to create account.", e);
			throw new CommandExecutionException(e);
		}
		connectionData.removeSessionAttribute(AccountsManager.IDENTIFIER);
		connectionData.removeSessionAttribute("flow");
		return responseString;
	}

}
