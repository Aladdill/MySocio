/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.io.InputStreamReader;
import java.net.URL;

import javax.jdo.annotations.PersistenceAware;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.SourcesManager;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class AddRssFeedExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(AddRssFeedExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		String url = connectionData.getRequestParameter("url");
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;
		try {
			feed = input.build(new InputStreamReader(new URL(url).openStream(), "UTF-8"));
		} catch (Exception e) {
			logger.error("Error verifying feed" + url, e);
			throw new NotValidRssUrlException("URL " + url + "is not a valid RSS url.", e);
		}
		RssSource source = new RssSource();
		source.setUrl(url);
		String title = feed.getTitle();
		source.setName(title);
		SocioTag tag = new SocioTag();
		tag.setIconType("rss.icon");
		tag.setValue(title);
		tag.setUniqueId(url);
		source.addTag(tag);
		SocioTag tag1 = new SocioTag();
		tag1.setIconType("rss.icon");
		tag1.setValue("rss.tag");
		tag1.setUniqueId("rss.tag");
		source.addTag(tag1);
		SocioUser user = connectionData.getUser();
		try {
			SourcesManager.addSourceToUser(user, source);
		} catch (Exception e) {
			logger.error("Source cuoldn't be added to user.", e);
			throw new CommandExecutionException(e);
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}
}
