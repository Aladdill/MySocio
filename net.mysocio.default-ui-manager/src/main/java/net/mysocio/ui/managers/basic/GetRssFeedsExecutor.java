/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.sources.rss.RssSource;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

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
		List<ISource> sources = user.getSources();
		List<RssSource> rssSources = new ArrayList<RssSource>();
		for (ISource source : sources) {
			if (source instanceof RssSource){
				rssSources.add((RssSource)source);
			}
		}
		return wrapRssSources(rssSources);
	}

	private String wrapRssSources(List<RssSource> rssSources) {
		StringBuffer output = new StringBuffer();
		for (RssSource rssSource : rssSources) {
			output.append("<div class='RssFeed'>");
			output.append(rssSource.getName());
			output.append("</div>");
		}
		return output.toString();
	}
}
