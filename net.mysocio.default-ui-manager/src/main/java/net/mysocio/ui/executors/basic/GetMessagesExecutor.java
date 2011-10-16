/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.util.Collections;
import java.util.List;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.IMessage;
import net.mysocio.ui.data.objects.DefaultMessage;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
public class GetMessagesExecutor implements ICommandExecutor {
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		StringBuffer output = new StringBuffer();
		List<? extends IMessage> messages = getMessages(connectionData);
		for (IMessage message : messages) {
			AbstractUiManager uiManager = new DefaultUiManager();
			String pageHtml = uiManager.getPage(new DefaultMessage(),connectionData.getUser());
			pageHtml = message.replacePlaceholders(pageHtml);
			output.append(pageHtml);
		}
		return output.toString();
	}
	
	private static List<IMessage> getMessages(IConnectionData connectionData) {
		String id = connectionData.getRequestParameter("sourceId");
		if (id == null){
			return Collections.emptyList();
		}
		SocioUser user = connectionData.getUser();
		user.setSelectedSource(id);
		DataManagerFactory.getDataManager().saveObject(user);
		return MessagesManager.getInstance().getMessagesForSelectedSource(user);
	}
}
