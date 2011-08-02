/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
public class logoutPageExecutor implements ICommandExecutor {

	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		SocioUser user = connectionData.getUser();
		if (user != null){
			connectionData.cleanSession();
		}
		return new DefaultUiManager().getLoginPage(connectionData.getLocale());
	}
}
