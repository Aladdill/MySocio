/**
 * 
 */
package net.mysocio.utils.rss;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.opml.Opml;
import com.sun.syndication.feed.opml.Outline;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.io.impl.OPML20Parser;

/**
 * @author Aladdin
 * 
 */
public class RssUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(RssUtils.class);

	public static void addRssFeed(String userId, String url, String title, SocioTag parent, UserTags userTags)
			throws AddingRssException {
		RssSource source = new RssSource();
		source.setUrl(url);
		source.setName(title);
		try {
			IDataManager dataManager = DataManagerFactory.getDataManager();
			dataManager.addSourceToUser(userId, source);
			userTags.createTag(url, title, parent);
		} catch (Exception e) {
			logger.error("Source couldn't be added to user.", e);
			throw new AddingRssException(e);
		}
	}
	public static void addRssFeed(String userId, String url, SocioTag parent, UserTags userTags)
			throws AddingRssException {
		String title = "";
		try {
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(new URL(url)));
			title = feed.getTitle();
		} catch (Exception e) {
			String message = "Error geting RSS title for url " + url;
			logger.error(message, e);
			throw new AddingRssException(message, e);
		}
		addRssFeed(userId, url, title, parent, userTags);
	}
	public static void importOpml(String userId, String url) throws Exception{
		URL feedURL = new URL(url);
		importOpml(userId, feedURL.openStream());
	}
	public static void importOpml(String userId, InputStream stream) throws Exception{
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new InputStreamReader(stream, "UTF-8"));
		importOpml(userId, document);
	}
	private static void importOpml(String userId, Document document) throws Exception {
		OPML20Parser parser = new OPML20Parser();
		Opml feed = (Opml) parser.parse(document, true);
		List<Outline> outlines = (List<Outline>) feed.getOutlines();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		UserTags userTags = dataManager.getUserTags(userId);
		SocioTag rssFeeds = getRssTag(userTags);
		for (Outline outline : outlines) {
			List<Outline> children = outline.getChildren();
			if (children.size() > 0) {
				SocioTag parent = userTags.createTag("RSS_" + outline.getTitle().replace(" ", "_"), outline.getTitle(), rssFeeds);
				for (Outline child : children) {
					addRssFeed(userId, child.getXmlUrl(), child.getTitle(), parent, userTags);
				}
			}
		}
		dataManager.saveObject(userTags);
	}
	public static SocioTag getRssTag(UserTags userTags) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioTag rssFeeds = userTags.createTag("rss.tag", "rss.tag", "rss.icon");
		dataManager.saveObject(userTags);
		return rssFeeds;
	}
}
