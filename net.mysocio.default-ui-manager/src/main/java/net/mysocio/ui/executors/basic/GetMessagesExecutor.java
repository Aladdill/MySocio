/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Collections;
import java.util.List;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class GetMessagesExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(GetContactsExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		StringBuffer output = new StringBuffer();
		List<? extends GeneralMessage> messages = getMessages(connectionData);
		AbstractUiManager uiManager = new DefaultUiManager();
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, connectionData.getLocale());
		String messagePage = "";
		for (GeneralMessage message : messages) {
			try {
				messagePage = uiManager.getPage(message.getUiCategory(), message.getUiName(), connectionData.getUserId());
			} catch (CorruptedDataException e) {
				logger.error("Failed showing messages",e);
				throw new CommandExecutionException(e);
			}
			String pageHtml = message.replacePlaceholders(messagePage);
			String date = formatter.format(new Date(message.getDate()));
			pageHtml = pageHtml.replace("date.long", Long.toString(message.getDate()));
			pageHtml = pageHtml.replace("message.date", date);
			output.append(pageHtml);
		}
		return output.toString();
	}
	
	private static List<GeneralMessage> getMessages(IConnectionData connectionData) {
		String tagId = connectionData.getRequestParameter("sourceId");
		if (tagId == null){
			return Collections.emptyList();
		}
		return MessagesManager.getInstance().getMessagesForSelectedSource(connectionData.getUserId(), tagId);
	}
}
