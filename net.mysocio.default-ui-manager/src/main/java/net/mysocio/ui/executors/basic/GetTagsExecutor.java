/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class GetTagsExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(GetTagsExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	private Map<String, Long> tagsMessagesCount = new HashMap<String, Long>();
	private Locale locale;
	private Collection<SocioTag> tags;
	@Override
	public String execute(IConnectionData connectionManager) throws CommandExecutionException{
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String userId = connectionManager.getUserId();
		SocioUser user = dataManager.getObject(SocioUser.class, userId);
		locale = new Locale(user.getLocale());
		tags = dataManager.getUserTags(userId);
		for (SocioTag tag : tags) {
			
			String tagId = tag.getId().toString();
			Long num = dataManager.countUnreadMessages(tagId);
			tagsMessagesCount.put(tagId, num);
		}
		JsonFactory f = new JsonFactory();
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = f.createJsonGenerator(writer);
			jsonGenerator.writeStartObject();
			jsonGenerator.writeObjectFieldStart("json_data");
			jsonGenerator.writeArrayFieldStart("data");
			addRootNode(jsonGenerator);
			jsonGenerator.writeEndArray();//for data
			jsonGenerator.writeEndObject();//for json_data
			jsonGenerator.writeArrayFieldStart("plugins");
			jsonGenerator.writeString("themes");
			jsonGenerator.writeString("json_data");
			jsonGenerator.writeString("ui");
			jsonGenerator.writeString("crrm");
			jsonGenerator.writeEndArray();//for plugins
			jsonGenerator.writeObjectFieldStart("themes");
			jsonGenerator.writeStringField("theme", "default");
			jsonGenerator.writeBooleanField("dots", false);
			jsonGenerator.writeBooleanField("icons", true);
			jsonGenerator.writeEndObject();//for themes
			jsonGenerator.writeObjectFieldStart("core");
			jsonGenerator.writeArrayFieldStart("initially_open");
			jsonGenerator.writeString(user.getSelectedTag());
			jsonGenerator.writeEndArray();//for initially_open
			jsonGenerator.writeEndObject();//for core
			jsonGenerator.writeObjectFieldStart("ui");
			jsonGenerator.writeArrayFieldStart("initially_select");
			jsonGenerator.writeString(user.getSelectedTag());
			jsonGenerator.writeEndArray();//for initially_select
			jsonGenerator.writeEndObject();//for ui
			jsonGenerator.writeEndObject();//final
			jsonGenerator.flush();
		} catch (Exception e) {
			logger.error("Can't create user sources tree.",e);
			throw new CommandExecutionException(e);
		}
		return writer.toString();
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
			String id, String icon, Long unreadMessagesNum) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeObjectFieldStart("attr");
		jsonGenerator.writeStringField("id", id);
		String title = name;
		if (unreadMessagesNum > 0){
			jsonGenerator.writeStringField("style", "font-weight: bold;");
			title = "(" + unreadMessagesNum + ")" + title;
		}
		jsonGenerator.writeEndObject();// for field 'attr'
		jsonGenerator.writeObjectFieldStart("data");
		jsonGenerator.writeStringField("title", title);
		jsonGenerator.writeEndObject();// for field 'data'
		if (icon != null){
			jsonGenerator.writeStringField("icon", icon);
		}
	}
	private void addRootNode(JsonGenerator jsonGenerator) throws Exception{
		jsonGenerator.writeStartObject();
		addNodeChildren(jsonGenerator);
		addNodeData(jsonGenerator, SocioUser.ALL_TAGS, SocioUser.ALL_TAGS, null, 0l);
		jsonGenerator.writeStringField("state", "open");
		jsonGenerator.writeEndObject();
	}
	
	private void addTagNode(JsonGenerator jsonGenerator, SocioTag tag) throws Exception{
		jsonGenerator.writeStartObject();
		String tagId = tag.getId().toString();
		Long unreadMessagesNum = tagsMessagesCount.get(tagId);
		if (unreadMessagesNum == null){
			unreadMessagesNum = 0l;
		}
		addNodeData(jsonGenerator, DefaultResourcesManager.getResource(locale, tag.getValue()), tagId, DefaultResourcesManager.getResource(locale, tag.getIconType()), unreadMessagesNum);
		jsonGenerator.writeEndObject();
	}
	
	private void addNodeChildren(JsonGenerator jsonGenerator) throws Exception{
		jsonGenerator.writeArrayFieldStart("children");
		for (SocioTag tag : tags) {
			addTagNode(jsonGenerator, tag);
		}
		jsonGenerator.writeEndArray(); // for field 'children'
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
