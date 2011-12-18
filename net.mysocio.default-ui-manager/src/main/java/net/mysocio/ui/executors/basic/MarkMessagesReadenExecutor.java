/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class MarkMessagesReadenExecutor implements ICommandExecutor{
	private static final Logger logger = LoggerFactory.getLogger(MarkMessagesReadenExecutor.class);
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		String messagesIds = connectionData.getRequestParameter("messagesIds");
		logger.debug("Marking readen message with ids: " + messagesIds);
		SocioUser user = connectionData.getUser();
		MessagesManager.getInstance().setMessagesReadden(user, messagesIds);
		try {
			MessagesManager.getInstance().updateUnreaddenMessages(user);
		} catch (Exception e) {
			logger.error("Can't get unreadden messages", e);
			throw new  CommandExecutionException(e);
		}
		return new GetSourcesExecutor().execute(connectionData);
	}
}
