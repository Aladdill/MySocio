/**
 * 
 */
package net.mysocio.data.management.camel;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioObject;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.utils.rss.RssUtils;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Transient;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author nathan
 *
 */
public class RSSFeedsProcessor extends SocioObject implements Processor {
	public static final String ACTIVEMQ_RSS_FEEDS = "activemq:rssFeeds";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4656771966871920349L;
	@Transient
	static final Logger logger = LoggerFactory.getLogger(RSSFeedsProcessor.class);

	@Override
	public void process(Exchange arg0) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<Source> rssSources = dataManager.getObjects(Source.class);
		for (Source source : rssSources) {
			if (!(source instanceof RssSource)){
				continue;
			}
			RssSource rssSource = (RssSource)source;
			String url = rssSource.getUrl();
			Long now = System.currentTimeMillis();
			Long lastFailure = rssSource.getLastFailure();
			Boolean blockedFeed = rssSource.isBlocked();
			Long updatedFeed = rssSource.getLastUpdate();
			try {
				if (logger.isDebugEnabled()){
					if (lastFailure != 0l){
						logger.debug("url : " + url + " failed " + new Date(lastFailure));
					}
					if (blockedFeed){
						logger.debug("url : " + url + " is blocked");
					}
					if (updatedFeed != 0l){
						logger.debug("url : " + url + " updated " + new Date(updatedFeed));
					}
				}
				//if connection failed, we want to retry only after 2 hours and update feeds only once at 15 minutes
				if (((now - lastFailure) > 1000*60*120) && !blockedFeed && ((now - updatedFeed) > 1000*60*15)){
					if (logger.isDebugEnabled()){
						logger.debug("Trying to get messages for feed: " + url);
					}
					SyndFeed feed = RssUtils.buldFeed(url);
					
					if (feed == null){
						return;
					}
					@SuppressWarnings("unchecked")
					List<SyndEntryImpl> entries = (List<SyndEntryImpl>)feed.getEntries();
			    	if (logger.isDebugEnabled()){
						logger.debug("Got " + entries.size() + " messages for feed: " + feed.getTitle());
					}
			    	((RssSource)rssSource).processMessages(feed, entries);
			    	rssSource.setLastUpdate(now);
				}
			} catch (ConnectException ce){
				rssSource.setLastFailure(now);
				logger.warn("Problem with connection " + url + " because of " + ce.getMessage());
			} catch (FileNotFoundException fnfe){
				rssSource.setBlocked(true);
				logger.warn("Rss url broken " + url);
			} catch (Exception e) {
				//if processor failed for some reason, we want to know it, but not to stop extraction process 
				rssSource.setLastFailure(now);
				logger.warn("Problem processing messages for RSS: " + url, e.getMessage());
			}
			dataManager.saveExistingObject(rssSource);
		}
	}
}
