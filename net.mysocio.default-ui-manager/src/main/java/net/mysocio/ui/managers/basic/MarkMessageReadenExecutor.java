/**
 * 
 */
package net.mysocio.ui.managers.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.management.MessagesManager;
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
		String messageId = connectionData.getRequestParameter("messageId");
		String sourceId = connectionData.getRequestParameter("sourceId");
		MessagesManager.getInstance().setMessageReadden(connectionData.getUser(), sourceId, messageId);
		return new GetSourcesExecutor().execute(connectionData);
	}

}
