/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.utils.rss.AddingRssException;
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
			RssUtils.addRssFeed(connectionData.getUserId(), url);
		} catch (AddingRssException e) {
			logger.error("Error occured while adding rss feed.");
			throw new CommandExecutionException(e);
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}
}
