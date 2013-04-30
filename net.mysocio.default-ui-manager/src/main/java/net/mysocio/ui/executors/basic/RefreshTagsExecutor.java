/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
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
public class RefreshTagsExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(RefreshTagsExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(javax.servlet.http.HttpServletRequest)
	 */
	private Map<String, Long> tagsMessagesCount = new HashMap<String, Long>();
	private Locale locale;
	private UserTags tags;
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		IDataManager dataManager = DataManagerFactory.getDataManager();
		locale = new Locale(connectionData.getLocale().toString());
		tags = connectionData.getUserTags();
		String selected = connectionData.getRequestParameter("selected");
		String userId = connectionData.getUserId();
		List<SocioTag> leaves = Collections.emptyList();
		SocioTag selectedTag = tags.getTag(tags.getSelectedTag());
		if (selected.equals("true")){
			leaves = selectedTag.getLeaves();
		}else{
			leaves = tags.getLeaves();
		}
		for (SocioTag tag : leaves) {
			String tagId = tag.getUniqueId();
			Long num = dataManager.countUnreadMessages(userId, tagId);
			tagsMessagesCount.put(tagId, num);
		}
		JsonFactory f = new JsonFactory();
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = f.createJsonGenerator(writer);
			jsonGenerator.writeStartArray();
			if (selected.equals("true")){
				Long unreadMessagesNum = addNodeChildren(jsonGenerator, selectedTag.getChildren());
				addNodeData(jsonGenerator, DefaultResourcesManager.getResource(locale, selectedTag.getValue()), selectedTag.getUniqueId(), unreadMessagesNum);
			}else{
				Long unreadMessagesNum = addNodeChildren(jsonGenerator, tags.getChildren());
				addNodeData(jsonGenerator, DefaultResourcesManager.getResource(locale, tags.getValue()), tags.getValue(), unreadMessagesNum);
			}
			jsonGenerator.writeEndArray();
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
	private void addNodeData(JsonGenerator jsonGenerator, String name, String id, Long unreadMessagesNum) throws IOException,
			JsonGenerationException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("id", Integer.toString(id.hashCode()));
		String title = name;
		if (unreadMessagesNum > 0){
			jsonGenerator.writeStringField("style", "font-weight: bold;");
			title = "(" + unreadMessagesNum + ")" + title;
		}else{
			if (unreadMessagesNum == 0l && !tags.isShowAll() && !id.equals(UserTags.ALL_TAGS)){
				jsonGenerator.writeStringField("style", "display: none;");
			}
		}
		jsonGenerator.writeStringField("name", title);
		jsonGenerator.writeEndObject();
	}
	
	private Long addTagNode(JsonGenerator jsonGenerator, SocioTag tag) throws Exception{
		Long unreadMessagesNum = tagsMessagesCount.get(tag.getUniqueId());
		List<SocioTag> children = tag.getChildren();
		
		
		// We are counting messages only for leaves, so either it's in tagsMessagesCount
		// or we count by children 
		if (!children.isEmpty()){
			unreadMessagesNum = addNodeChildren(jsonGenerator, children);
		}
		String tagId = tag.getUniqueId();
		addNodeData(jsonGenerator, DefaultResourcesManager.getResource(locale, tag.getValue()), tagId, unreadMessagesNum);
		
		return unreadMessagesNum;
	}
	
	private Long addNodeChildren(JsonGenerator jsonGenerator, List<SocioTag> children) throws Exception{
		Long unreadMessagesNum = 0l;
		Collections.sort(children);
		for (SocioTag tag : children) {
			unreadMessagesNum += addTagNode(jsonGenerator, tag);
		}
		return unreadMessagesNum;
	}
}
