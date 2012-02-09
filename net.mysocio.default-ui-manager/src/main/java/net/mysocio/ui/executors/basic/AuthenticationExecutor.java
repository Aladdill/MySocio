/**
 * 
 */
package net.mysocio.ui.executors.basic;

import javax.jdo.annotations.PersistenceAware;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.InvalidNetworkException;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class AuthenticationExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationExecutor.class);

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		String identifier = connectionData.getRequestParameter(AccountsManager.IDENTIFIER);
		connectionData.setSessionAttribute(AccountsManager.IDENTIFIER, identifier);
		logger.debug("Authenticating acount of type:" + identifier);
		String accountRequestUrl = null;
		try {
			accountRequestUrl = AccountsManager.getInstance().getAccountRequestUrl(identifier);
		} catch (InvalidNetworkException e) {
			logger.error("Authentication for network " + identifier + " is missing.",e);
			throw new CommandExecutionException(e);
		}
		return accountRequestUrl;
	}
}
