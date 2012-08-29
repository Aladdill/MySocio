/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class LoginPageExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(LoginPageExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		String userId = connectionData.getUserId();
		if (userId != null){
			return new LoadMainPageExecutor().execute(connectionData);
		}
		String loginPage = "";
		try {
			loginPage = new DefaultUiManager().getLoginPage(connectionData.getLocale());
		} catch (CorruptedDataException e) {
			logger.error("Failed showing login page.",e);
			throw new CommandExecutionException(e);
		}
		return loginPage;
	}
}
