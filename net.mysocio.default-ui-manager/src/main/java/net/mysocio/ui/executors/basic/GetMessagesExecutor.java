/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Collections;
import java.util.List;

import javax.jdo.annotations.PersistenceAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
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
@PersistenceAware
public class GetMessagesExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(GetContactsExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		StringBuffer output = new StringBuffer();
		List<? extends IMessage> messages = getMessages(connectionData);
		AbstractUiManager uiManager = new DefaultUiManager();
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, connectionData.getLocale());
		String messagePage = "";
		for (IMessage message : messages) {
			try {
				messagePage = uiManager.getPage(message.getUiCategory(), message.getUiName(), connectionData.getUser());
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
	
	private static List<IMessage> getMessages(IConnectionData connectionData) {
		String id = connectionData.getRequestParameter("sourceId");
		if (id == null){
			return Collections.emptyList();
		}
		SocioUser user = connectionData.getUser();
		user.setSelectedSource(id);
		return MessagesManager.getInstance().getMessagesForSelectedSource(user);
	}
}
