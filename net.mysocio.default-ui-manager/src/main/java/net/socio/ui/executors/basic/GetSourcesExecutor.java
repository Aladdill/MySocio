/**
 * 
 */
package net.socio.ui.executors.basic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.ISourcesGroup;
import net.mysocio.data.SocioUser;
import net.mysocio.data.UnreaddenMessages;
import net.mysocio.ui.management.ICommandExecutor;

/**
 * @author Aladdin
 *
 */
public class GetSourcesExecutor implements ICommandExecutor {
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(SocioUser user, Map parameters) {
		StringBuffer out = new StringBuffer();
		out.append("<?xml version='1.0' encoding='UTF-8'?>" +
				"<tree id=\"0\">" +
		"<item text=\"All\" id=\"All\">");
		addSourcesTree(user, out);
		out.append("</item></tree>");
		return out.toString();
	}

	private void addSourcesTree(SocioUser user, StringBuffer output){
		Set<ISourcesGroup> sourcesGroups = user.getSourcesGroups();
		for (ISourcesGroup sourcesGroup : sourcesGroups) {
			List<ISource> sources = sourcesGroup.getSources();
			StringBuffer sourcesBuffer = new StringBuffer();
			Integer unreadMsg = 0;
			for (ISource source : sources) {
				Long id = source.getId();
				UnreaddenMessages unreadMessages = user.getUnreadMessages(id);
				String style = getReadenStyle();
				String messagesNum = new String();
				if (unreadMessages != null){
					style = getUnreadenStyle();
					int size = unreadMessages.getMessages().size();
					unreadMsg+=size;
					messagesNum = "(" + size + ")";
				}
				sourcesBuffer.append("<item text=\"").append(stripNonValidXMLCharacters(source.getName())).append(messagesNum).append("\" id=\"" + id +"\"").append(style).append("/>");
			}
			String messagesNum = new String();
			String style = getReadenStyle();
			if (unreadMsg > 0){
				style = getUnreadenStyle();
				messagesNum = "(" + unreadMsg + ")";
			}
			output.append("<item text=\"").append(stripNonValidXMLCharacters(sourcesGroup.getName())).append(messagesNum).append("\" id=\"" + sourcesGroup.getId() +"\"").append(style).append(">");
			output.append(sourcesBuffer).append("</item>");
		}
	}

	private String getUnreadenStyle() {
		return " style=\"font-weight: bold;\" ";
	}

	private String getReadenStyle() {
		return "";
	}

	/**
	 * This method ensures that the output String has only
	 * valid XML unicode characters as specified by the
	 * XML 1.0 standard. For reference, please see
	 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
	 * standard</a>. This method will return an empty
	 * String if the input is null or empty.
	 *
	 * @param in The String whose non-valid characters we want to remove.
	 * @return The in String, stripped of non-valid characters.
	 */
	public String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		Character current; // Used to reference the current character.

		if (in == null || ("".equals(in))) return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if (((current == 0x9) ||
					(current == 0xA) ||
					(current == 0xD) ||
					((current >= 0x20) && (current <= 0xD7FF)) ||
					((current >= 0xE000) && (current <= 0xFFFD)) ||
					((current >= 0x10000) && (current <= 0x10FFFF))) && !current.equals('\"'))
				out.append(current);
		}
		return out.toString();
	}    

}
