/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Aladdin
 *
 */
public class RemoveRssFeedExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(RemoveRssFeedExecutor.class);
	/** 
	 * For now, I'm just removing rss feed from User, in future maybe reference counted management should be implemented   
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String tagId = connectionData.getRequestParameter("id");
		UserTags userTags = connectionData.getUserTags();
		SocioTag tag = userTags.getTag(tagId);
		if (tag.getChildren().isEmpty()){
			userTags.removeTag(tagId);
			try {
				dataManager.saveObject(userTags);
				Source source = dataManager.getSource(tagId);
				if (source != null){
					source.removeProcessor(connectionData.getUserId());
				}
			} catch (Exception e) {
				logger.error("Can't remove rss feed.",e);
				throw new CommandExecutionException(e);
			}
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}

}
