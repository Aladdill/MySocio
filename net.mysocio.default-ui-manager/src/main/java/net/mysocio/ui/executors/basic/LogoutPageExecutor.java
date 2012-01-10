/**
 * 
 */
package net.mysocio.ui.executors.basic;

import javax.jdo.annotations.PersistenceAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class LogoutPageExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(LogoutPageExecutor.class);
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		SocioUser user = connectionData.getUser();
		if (user != null){
			connectionData.cleanSession();
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
