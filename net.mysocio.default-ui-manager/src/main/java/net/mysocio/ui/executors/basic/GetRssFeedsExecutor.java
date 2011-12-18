/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.data.objects.AddRssLine;
import net.mysocio.ui.data.objects.RssLine;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
public class GetRssFeedsExecutor implements ICommandExecutor {

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
	throws CommandExecutionException {
		SocioUser user = connectionData.getUser();
		AbstractUiManager uiManager = new DefaultUiManager();
		List<Source> sources = user.getSources();
		String page = uiManager.getPage(new AddRssLine(),user);
		Locale locale = new Locale(user.getLocale());
		page = page.replace("rss.icon", DefaultResourcesManager.getResource(locale, ("rss.icon")));
		String feed = uiManager.getPage(new RssLine(),user);
		for (Source source : sources) {
			if (source instanceof RssSource){
				String currentFeed = feed.replace("rss.name", source.getName());
				currentFeed = currentFeed.replace("rss.id", source.getId());
				currentFeed = currentFeed.replace("rss.icon", DefaultResourcesManager.getResource(locale, ("rss.icon")));
				page += currentFeed;
			}
		}
		return page;
	}
}
