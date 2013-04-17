/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.management.UnapprovedUserException;

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
			String userId = connectionData.getUserId();
			if (userId != null) {
				logger.debug("Creating account for user with id: " + userId);
				Account account = AccountsManager.getInstance().getAccount(connectionData);
				IDataManager dataManager = DataManagerFactory.getDataManager();
				dataManager.addAccountToUser(account, userId, connectionData.getUserTags());
			}else{
				logger.debug("Attempt was made to create account before login.");
			}
		}catch (Exception e) {
			logger.error("Failed to create account.", e);
			if (e instanceof UnapprovedUserException){
				throw new CommandExecutionException(e.getMessage());
			}
			throw new CommandExecutionException(e);
		}
		connectionData.removeSessionAttribute(AccountsManager.IDENTIFIER);
		connectionData.removeSessionAttribute("code");
		return "";
	}

}
