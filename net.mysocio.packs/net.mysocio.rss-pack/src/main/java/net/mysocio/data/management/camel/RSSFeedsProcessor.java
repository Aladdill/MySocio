/**
 * 
 */
package net.mysocio.data.management.camel;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Transient
	private Map<String, Long> failures = new HashMap<String, Long>();

	@Override
	public void process(Exchange arg0) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<Source> rssSources = dataManager.getObjects(Source.class);
		for (Source rssSource : rssSources) {
			if (!(rssSource instanceof RssSource)){
				continue;
			}
			String url = rssSource.getUrl();
			if (logger.isDebugEnabled()){
				logger.debug("Trying to get messages for feed: " + url);
			}
			Long now = System.currentTimeMillis();
			Long lastFailure = failures.get(url);
			try {
				
				//if connection failed, we want to retry only after 15 minutes
				if (lastFailure == null || (now - lastFailure) > 1000*60*15){
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
				}
			} catch (ConnectException ce){
				failures.put(url, now);
				logger.warn("Problem with connection " + url + " because of " + ce.getMessage());
			} catch (Exception e) {
				//if processor failed for some reason, we want to know it, but not to stop extraction process 
				failures.put(url, now);
				logger.warn("Problem processing messages for RSS: " + url, e);
			}			
		}
	}
}
