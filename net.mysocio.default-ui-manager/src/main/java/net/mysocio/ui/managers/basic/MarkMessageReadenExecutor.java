/**
 * 
 */
package net.mysocio.ui.managers.basic;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.executors.basic.GetSourcesExecutor;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class MarkMessageReadenExecutor implements ICommandExecutor{
	private static final Logger logger = LoggerFactory.getLogger(MarkMessageReadenExecutor.class);
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		SocioUser user = connectionData.getUser();
		String messageId = connectionData.getRequestParameter("messageId");
		try {
			user.setMessageReadden(messageId);
			DataManagerFactory.getDataManager().saveObject(user);
		} catch (CorruptedDataException e) {
			logger.error("marking message unread failed for message with id " + messageId,e);
			throw new CommandExecutionException(e);
		}
		return new GetSourcesExecutor().execute(connectionData);
	}

}
