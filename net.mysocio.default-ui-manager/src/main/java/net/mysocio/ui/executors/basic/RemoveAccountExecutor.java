/**
 * 
 */
package net.mysocio.ui.executors.basic;

import javax.jdo.annotations.PersistenceAware;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class RemoveAccountExecutor implements ICommandExecutor {

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

}
