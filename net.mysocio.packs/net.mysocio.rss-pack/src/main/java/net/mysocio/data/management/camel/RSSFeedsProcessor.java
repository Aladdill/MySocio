/**
 * 
 */
package net.mysocio.data.management.camel;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Transient
	private Map<String, Long> failures = new HashMap<String, Long>();

	@Override
	public void process(Exchange arg0) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<RssSource> rssSources = dataManager.getObjects(RssSource.class);
		for (RssSource rssSource : rssSources) {
			if (logger.isDebugEnabled()){
				logger.debug("Trying to get messages for feed: " + rssSource.getUrl());
			}
			Long now = System.currentTimeMillis();
			Long lastFailure = failures.get(rssSource.getUrl());
			try {
				
				//if connection failed, we want to retry only after 15 minutes
				if (lastFailure == null || (now - lastFailure) > 1000*60*15){
					SyndFeed feed = RssUtils.buldFeed(rssSource.getUrl());
					
					if (feed == null){
						return;
					}
					List<SyndEntryImpl> entries = feed.getEntries();
			    	if (logger.isDebugEnabled()){
						logger.debug("Got " + entries.size() + " messages for feed: " + feed.getTitle());
					}
			    	rssSource.processMessages(feed, entries);
				}
			} catch (ConnectException ce){
				failures.put(rssSource.getUrl(), now);
				logger.warn("Problem with connection " + rssSource.getUrl() + " because of " + ce.getMessage());
			} catch (Exception e) {
				//if processor failed for some reason, we want to know it, but not to stop extraction process 
				failures.put(rssSource.getUrl(), now);
				logger.warn("Problem processing messages for RSS: " + rssSource.getUrl(), e);
			}			
		}
	}
}
