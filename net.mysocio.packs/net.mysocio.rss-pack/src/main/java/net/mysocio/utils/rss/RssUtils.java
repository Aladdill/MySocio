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
import net.mysocio.data.management.DataManagerFactory;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.opml.Opml;
import com.sun.syndication.feed.opml.Outline;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.impl.OPML20Parser;

/**
 * @author Aladdin
 * 
 */
public class RssUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(RssUtils.class);

	public static void addRssFeed(String userId, String url)
			throws AddingRssException {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;
		try {
			feed = input.build(new InputStreamReader(new URL(url).openStream(),
					"UTF-8"));
		} catch (Exception e) {
			logger.error("Error verifying feed" + url, e);
			throw new AddingRssException("URL " + url
					+ "is not a valid RSS url.", e);
		}
		RssSource source = new RssSource();
		source.setUrl(url);
		String title = feed.getTitle();
		source.setName(title);
		SocioTag tag = new SocioTag();
		tag.setIconType("rss.icon");
		tag.setValue(title);
		source.addTag(tag);
		SocioTag tag1 = new SocioTag();
		tag1.setIconType("rss.icon");
		tag1.setValue("rss.tag");
		source.addTag(tag1);
		try {
			IDataManager dataManager = DataManagerFactory.getDataManager();
			dataManager.addSourceToUser(userId, source);
		} catch (Exception e) {
			logger.error("Source couldn't be added to user.", e);
			throw new AddingRssException(e);
		}
	}
	public static void importOpml(String userId, String url) throws Exception{
		URL feedURL = new URL(url);
		importOpml(userId, feedURL.openStream());
	}
	public static void importOpml(String userId, InputStream stream) throws Exception{
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(stream);
		importOpml(userId, document);
	}
	private static void importOpml(String userId, Document document) throws Exception {
		OPML20Parser parser = new OPML20Parser();
		Opml feed = (Opml) parser.parse(document, true);
		List<Outline> outlines = (List<Outline>) feed.getOutlines();
		for (Outline outline : outlines) {
			List<Outline> children = outline.getChildren();
			if (children.size() > 0) {
				for (Outline child : children) {
					addRssFeed(userId, child.getXmlUrl());
				}
			}
		}
	}
}
