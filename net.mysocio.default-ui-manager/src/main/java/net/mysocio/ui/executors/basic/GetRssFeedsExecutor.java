/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.data.objects.AddRssLine;
import net.mysocio.ui.data.objects.RssLine;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class GetRssFeedsExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(GetRssFeedsExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
	throws CommandExecutionException {
		String userId = connectionData.getUserId();
		AbstractUiManager uiManager = new DefaultUiManager();
		List<Source> sources = user.getSources();
		String page = "";
		try {
			page = uiManager.getPage(AddRssLine.CATEGORY, AddRssLine.NAME, userId);
			Locale locale = new Locale(user.getLocale());
			page = page.replace("rss.icon", DefaultResourcesManager.getResource(locale, ("rss.icon")));
			String feed = uiManager.getPage(RssLine.CATEGORY, RssLine.NAME, userId);
			for (Source source : sources) {
				if (source instanceof RssSource){
					String currentFeed = feed.replace("rss.name", source.getName());
					currentFeed = currentFeed.replace("rss.id", source.getId().toString());
					currentFeed = currentFeed.replace("rss.icon", DefaultResourcesManager.getResource(locale, ("rss.icon")));
					page += currentFeed;
				}
			}
		} catch (CorruptedDataException e) {
			logger.error("Failed showing RSS feeds.",e);
			throw new CommandExecutionException(e);
		}
		return page;
	}
}
