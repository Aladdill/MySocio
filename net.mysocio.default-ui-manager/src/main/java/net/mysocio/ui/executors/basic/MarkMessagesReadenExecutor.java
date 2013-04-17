/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.management.DataManagerFactory;
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
		String markAll = connectionData.getRequestParameter("markAll");
		logger.debug("Marking readen message with ids: " + messagesIds);
		try {
			String userId = connectionData.getUserId();
			if (messagesIds != null && !messagesIds.isEmpty()){
				MessagesManager.getInstance().setMessagesReadden(userId, messagesIds);
			}
			if (markAll != null && !markAll.isEmpty()){
				DataManagerFactory.getDataManager().setMessagesReadden(userId,connectionData.getSelectedTag(),connectionData.getUserTags());
			}
		} catch (Exception e) {
			throw new CommandExecutionException("Can't set message as readden", e);
		}
		return "";
	}
}
