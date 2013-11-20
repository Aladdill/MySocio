/**
 * 
 */
package net.mysocio.utils.rss;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.syndication.feed.opml.Opml;
import com.sun.syndication.feed.opml.Outline;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
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
			dataManager.saveObject(userTags);
		} catch (Exception e) {
			logger.error("Source couldn't be added to user.", e);
			throw new AddingRssException(e);
		}
	}
	public static void addRssFeed(String userId, String url, UserTags userTags)
			throws AddingRssException {
		try {
			SyndFeed feed = buldFeed(url);
			String title = feed.getTitle();
			SocioTag rssFeeds = getRssTag(userTags);
			addRssFeed(userId, url, title, rssFeeds, userTags);
		} catch (Exception e) {
			String message = "Error adding RSS title for url " + url;
			logger.error(message, e);
			throw new AddingRssException(message, e);
		}
	}
	public static SyndFeed buldFeed(String url) throws FeedException,
			IOException, MalformedURLException {
		URLConnection cx = new URL(url).openConnection();
		cx.setRequestProperty("User-Agent", "http://www.mysocio.net; nathan@mysocio.net");
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(cx));
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
		@SuppressWarnings("unchecked")
		List<Outline> outlines = (List<Outline>) feed.getOutlines();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		UserTags userTags = dataManager.getUserTags(userId);
		SocioTag rssFeeds = getRssTag(userTags);
		for (Outline outline : outlines) {
			@SuppressWarnings("unchecked")
			List<Outline> children = outline.getChildren();
			String title = outline.getTitle();
			if (title == null){
				title = outline.getText();
			}
			if (children.size() > 0) {
				SocioTag parent = userTags.createTag("RSS_" + title.replace(" ", "_"), title, rssFeeds);
				for (Outline child : children) {
					String childTitle = child.getTitle();
					if (childTitle == null){
						childTitle = child.getText();
					}
					addRssFeed(userId, child.getXmlUrl(), childTitle, parent, userTags);
				}
			}else{
				addRssFeed(userId, outline.getXmlUrl(), title, rssFeeds, userTags);
			}
		}
	}
	public static void importGoogleReaderFeeds(String userId, String data) throws Exception{
		IDataManager dataManager = DataManagerFactory.getDataManager();
		UserTags userTags = dataManager.getUserTags(userId);
		SocioTag rssFeeds = getRssTag(userTags);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory
				.newInstance();
		InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(data));
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = dBuilder.parse(is);
		NodeList nList = doc.getChildNodes();
		if (nList.getLength() > 0){
			nList = nList.item(0).getChildNodes();
			if (nList.getLength() > 0){
				nList = nList.item(0).getChildNodes();
				for (int i = 0; i < nList.getLength(); i++) {
					Node item = nList.item(i);
					if (item.getNodeName().equals("object")) {
						Node urlTag = item.getChildNodes().item(0);
						String url = urlTag.getChildNodes().item(0).getNodeValue().substring(5);
						Node titleTag = item.getChildNodes().item(1);
						String title = titleTag.getChildNodes().item(0).getNodeValue();
						Node categoriesTag = item.getChildNodes().item(2);
						NodeList categories = categoriesTag.getChildNodes();
						for (int j = 0; j < categories.getLength(); j++) {
							Node item1 = categories.item(j);
							if (item1.getNodeName().equals("object")) {
								Node lableTag = item1.getChildNodes().item(1);
								String label = lableTag.getChildNodes().item(0).getNodeValue();
								SocioTag parent = userTags.createTag("RSS_" + label.replace(" ", "_"), label, rssFeeds);
								addRssFeed(userId, url, title, parent, userTags);
							}
						}
					}
				}
			}
		}
	}
	public static SocioTag getRssTag(UserTags userTags) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioTag rssFeeds = userTags.createTag("rss.tag", "rss.tag", "rss.icon");
		dataManager.saveObject(userTags);
		return rssFeeds;
	}
}
