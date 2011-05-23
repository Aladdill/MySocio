/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author gurfinke
 *
 */
public class LoginPageExecutor implements ICommandExecutor {

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		return new DefaultUiManager().getLoginPage(connectionData.getLocale());
	}
}
