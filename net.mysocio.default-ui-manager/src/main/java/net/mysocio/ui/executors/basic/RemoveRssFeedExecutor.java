/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

/**
 * @author Aladdin
 *
 */
public class RemoveRssFeedExecutor implements ICommandExecutor {

	/** 
	 * For now, I'm just removing rss feed from User, in future maybe reference counted management should be implemented   
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		SocioUser user = connectionData.getUser();
		user.removeSource(connectionData.getRequestParameter("id"));
		return new GetRssFeedsExecutor().execute(connectionData);
	}

}
