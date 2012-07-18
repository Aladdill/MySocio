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
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
public class CreateAccountExecutor implements ICommandExecutor {
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
		try {
			SocioUser user = connectionData.getUser();
			Account account = AccountsManager.getInstance().getAccount(connectionData);
			IDataManager dataManager;
			if (user == null) {
				logger.debug("Creating account for new user.");
				dataManager = DataManagerFactory.getDataManager();
				user = dataManager.getUser(account,connectionData.getLocale());
			} else {
				logger.debug("Creating account for user: " + user.getName());
				dataManager = DataManagerFactory.getDataManager();
				dataManager.addAccountToUser(account, user);
			}
			connectionData.setUser(user);
		} catch (Exception e) {
			logger.error("Failed to create account.", e);
			throw new CommandExecutionException(e);
		}
		connectionData.removeSessionAttribute(AccountsManager.IDENTIFIER);
		connectionData.removeSessionAttribute("code");
		return "";
	}

}
