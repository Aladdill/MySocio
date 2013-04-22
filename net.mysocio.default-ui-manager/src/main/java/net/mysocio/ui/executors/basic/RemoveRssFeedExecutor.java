/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

/**
 * @author Aladdin
 *
 */
public class RemoveRssFeedExecutor implements ICommandExecutor {
//	private static final Logger logger = LoggerFactory.getLogger(RemoveRssFeedExecutor.class);
	/** 
	 * For now, I'm just removing rss feed from User, in future maybe reference counted management should be implemented   
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String tagId = connectionData.getRequestParameter("id");
		UserTags userTags = connectionData.getUserTags();
		userTags.removeTag(tagId);
		try {
			dataManager.saveObject(userTags);
			Source source = dataManager.getSource(tagId);
			source.removeRoute(connectionData.getUserId());
		} catch (Exception e) {
			throw new CommandExecutionException(e);
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}

}
