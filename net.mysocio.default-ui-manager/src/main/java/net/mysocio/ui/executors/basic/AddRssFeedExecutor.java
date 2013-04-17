/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.utils.rss.RssUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class AddRssFeedExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(AddRssFeedExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		String url = connectionData.getRequestParameter("url");
		try {
			String userId = connectionData.getUserId();
			IDataManager dataManager = DataManagerFactory.getDataManager();
			UserTags userTags = dataManager.getUserTags(userId);
			RssUtils.addRssFeed(userId, url, RssUtils.getRssTag(userTags), userTags);
			dataManager.saveObject(userTags);
		} catch (Exception e) {
			logger.error("Error occured while adding rss feed.");
			throw new CommandExecutionException(e);
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}
}
