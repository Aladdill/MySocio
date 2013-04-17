/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.util.List;
import java.util.Locale;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.data.objects.AddRssLine;
import net.mysocio.ui.data.objects.ImportOPML;
import net.mysocio.ui.data.objects.RssLine;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;
import net.mysocio.utils.rss.RssUtils;

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
		StringBuffer page = new StringBuffer();
		try {
			Locale locale = connectionData.getLocale();
			UserTags userTags = connectionData.getUserTags();
			SocioTag rssTag = RssUtils.getRssTag(userTags);
			List<SocioTag> leaves = rssTag.getLeaves();
			String addRss = uiManager.getPage(AddRssLine.CATEGORY, AddRssLine.NAME, userId);
			page.append(addRss.replace("rss.icon", DefaultResourcesManager.getResource(locale, ("rss.icon"))));
			String importOPML = uiManager.getPage(ImportOPML.CATEGORY, ImportOPML.NAME, userId);
			page.append(importOPML.replace("import.opml", DefaultResourcesManager.getResource(locale, ("import.opml"))));
			String feed = uiManager.getPage(RssLine.CATEGORY, RssLine.NAME, userId);
			for (SocioTag tag : leaves) {
					String currentFeed = feed.replace("rss.name", tag.getValue());
					currentFeed = currentFeed.replace("rss.id", tag.getUniqueId());
					currentFeed = currentFeed.replace("rss.icon", DefaultResourcesManager.getResource(locale, ("rss.icon")));
					page.append(currentFeed);
			}
		} catch (Exception e) {
			logger.error("Failed showing RSS feeds.",e);
			throw new CommandExecutionException(e);
		}
		return page.toString();
	}
}
