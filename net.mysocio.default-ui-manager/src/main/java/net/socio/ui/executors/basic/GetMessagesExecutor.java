/**
 * 
 */
package net.socio.ui.executors.basic;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.mysocio.data.IMessage;
import net.mysocio.data.SocioUser;
import net.mysocio.data.UnreaddenMessages;
import net.mysocio.ui.management.ICommandExecutor;
import net.socio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
public class GetMessagesExecutor implements ICommandExecutor {
	public static final String MESSAGE_TEXT = "i";
	public static final String MESSAGE_TITLE = "b";

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(SocioUser user, Map parameters) {
		StringBuffer output = new StringBuffer();
		List<? extends IMessage> messages = getMessages(user, parameters);
		for (IMessage message : messages) {
			output.append(wrapXmlMessage(message.getId(), message.getTitle(), message.getLink(), message.getText()));
		}
		return output.toString();
	}
	
	private static List<? extends IMessage> getMessages(SocioUser user, Map parameters) {
		Object id = parameters.get(DefaultUiManager.SOURCE_ID);
		if (id == null){
			return Collections.emptyList();
		}
		String[] ids = (String[])id;
		UnreaddenMessages unreadMessages = user.getUnreadMessages(Long.parseLong(ids[0]));
		if (unreadMessages == null){
			return Collections.emptyList();
		}
		return unreadMessages.getMessages();
	}
	
	/**
	 * @param output
	 * @param text
	 */
	private static String wrapXmlMessage(Long id, String title, String link, String text) {
		StringBuffer output = new StringBuffer();
		output.append("<div style='padding: 10px; border:1px solid #A4BED4;' id=\"").append(id).append("\">");
		String actualTitle = "No Title";
		if (title != null && title.length() >= 0){
			actualTitle = title;
		}
		output.append("<div style='padding: 10px;'\">");
		output.append("<a href=\""+ link + "\" target=\"_blank\"><").append(MESSAGE_TITLE).append(">").append(actualTitle).append("</").append(MESSAGE_TITLE).append("></a>");
		output.append("</div>");
		output.append("<").append(MESSAGE_TEXT).append(">").append(text).append("</").append(MESSAGE_TEXT).append(">");
		output.append("</div>");
		return output.toString();
	}
}
