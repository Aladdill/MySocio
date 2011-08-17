/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

/**
 * @author Aladdin
 *
 */
public class GetSourcesExecutor implements ICommandExecutor {
	private DefaultUiManager uiManager = new DefaultUiManager();
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String execute(IConnectionData connectionManager) throws CommandExecutionException{
		SocioUser user = connectionManager.getUser();
		DataManagerFactory.getDataManager().updateUnreaddenMessages(user);
		JsonFactory f = new JsonFactory();
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = f.createJsonGenerator(writer);
			addRootNode(jsonGenerator, user);
			jsonGenerator.flush();
		} catch (Exception e) {
			throw new CommandExecutionException(e);
		}
		return jsonGenerator.toString();
	}

	/**
	 * @param jsonGenerator
	 * @param name
	 * @param id
	 * @param icon
	 * @param unreaden
	 * @throws IOException
	 * @throws JsonGenerationException
	 */
	private void addNodeData(JsonGenerator jsonGenerator, String name,
			String id, String icon, int unreadMessagesNum) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeObjectFieldStart("data");
		if (unreadMessagesNum > 0){
			jsonGenerator.writeObjectFieldStart("attr");
			jsonGenerator.writeStringField("style", "font-weight: bold;");
			jsonGenerator.writeEndObject();// for field 'data'
			jsonGenerator.writeStringField("title", name + "(" + unreadMessagesNum + ")");
		}else{
			jsonGenerator.writeStringField("title", name);
		}
		jsonGenerator.writeEndObject();// for field 'data'
		jsonGenerator.writeObjectFieldStart("metadata");
		jsonGenerator.writeStringField("id", id);
		jsonGenerator.writeEndObject(); // for field 'metadata'
		if (icon != null){
			jsonGenerator.writeStringField("icon", "images/" + icon);
		}
	}
	private void addRootNode(JsonGenerator jsonGenerator, SocioUser user) throws Exception{
		jsonGenerator.writeStartObject();
		int unreadMessagesNum = addNodeChildren(jsonGenerator, user.getSortedSources(), user);
		addNodeData(jsonGenerator, SocioUser.ALL_SOURCES, SocioUser.ALL_SOURCES, null, unreadMessagesNum );
		jsonGenerator.writeEndObject();
	}
	
	private int addTagNode(JsonGenerator jsonGenerator, String tag, List<ISource> sources, SocioUser user) throws Exception{
		jsonGenerator.writeStartObject();
		int unreadMessagesNum = addNodeChildren(jsonGenerator, sources, user);
		addNodeData(jsonGenerator, tag, "tag_" + tag, uiManager.getTagIcon(tag), unreadMessagesNum );
		jsonGenerator.writeEndObject();
		return unreadMessagesNum;
	}
	private int addSourceNode(JsonGenerator jsonGenerator, ISource source, SocioUser user) throws Exception{
		jsonGenerator.writeStartObject();
		Integer unreadMessagesNum = user.getUnreadMessagesNum(source.getId());
		addNodeData(jsonGenerator, source.getName(), source.getId(), uiManager.getSourceIcon(source.getClass()), unreadMessagesNum);
		jsonGenerator.writeEndObject();
		return unreadMessagesNum;
	}
	
	private int addNodeChildren(JsonGenerator jsonGenerator, List<ISource> sources, SocioUser user) throws Exception{
		jsonGenerator.writeArrayFieldStart("children");
		Integer unreadMessagesNum = 0;
		for (ISource source : sources) {
			unreadMessagesNum += addSourceNode(jsonGenerator, source, user);
		}
		jsonGenerator.writeEndArray(); // for field 'children'
		return unreadMessagesNum;
	}
	private int addNodeChildren(JsonGenerator jsonGenerator, Map<String, List<ISource>> sortedSources, SocioUser user) throws Exception{
		jsonGenerator.writeArrayFieldStart("children");
		Integer unreadMessagesNum = 0;
		for (String tag : sortedSources.keySet()) {
			unreadMessagesNum += addTagNode(jsonGenerator, tag, sortedSources.get(tag), user);
		}
		jsonGenerator.writeEndArray(); // for field 'children'
		return unreadMessagesNum;
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
