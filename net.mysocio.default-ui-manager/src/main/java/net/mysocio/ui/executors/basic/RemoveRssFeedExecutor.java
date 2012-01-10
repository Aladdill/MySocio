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

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class RemoveRssFeedExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(RemoveRssFeedExecutor.class);
	/** 
	 * For now, I'm just removing rss feed from User, in future maybe reference counted management should be implemented   
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		SocioUser user = connectionData.getUser();
		try {
			user.removeSource(connectionData.getRequestParameter("id"));
		} catch (CorruptedDataException e) {
			logger.error("Can't remove source.",e);
			throw new CommandExecutionException(e);
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}

}
