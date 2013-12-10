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
public class LJFeedsProcessor extends SocioObject implements Processor {
	public static final String ACTIVEMQ_LJ_FEEDS = "ljFeeds";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4656771966871920349L;
	@Transient
	static final Logger logger = LoggerFactory.getLogger(LJFeedsProcessor.class);

	@Override
	public void process(Exchange arg0) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<Source> rssSources = dataManager.getObjects(Source.class);
		int i = 0;
		logger.info("Starting LJ messages extraction.");
		boolean connectionFail = false;
		for (Source source : rssSources) {
			if (!(source instanceof RssSource)){
				continue;
			}
			
			RssSource rssSource = (RssSource)source;
			String url = rssSource.getUrl();
			//Because of behavior of LJ if once connection failed we'll wait till next round 
			if (url == null || !url.contains("livejournal.com") || connectionFail){
				continue;
			}
			i++;
			Long now = System.currentTimeMillis();
			Long lastFailure = rssSource.getLastFailure();
			Boolean blockedFeed = rssSource.isBlocked();
			Long updatedFeed = rssSource.getLastUpdate();
			try {
				boolean debug = logger.isDebugEnabled();
				if (debug){
					if (lastFailure != 0l){
						logger.debug("url : " + url + " failed " + new Date(lastFailure));
					}
					if (blockedFeed){
						logger.debug("url : " + url + " is blocked");
					}
					if (updatedFeed != 0l){
						logger.debug("url : " + url + " updated " + new Date(updatedFeed));
					}
					logger.debug("get messages if " + (!blockedFeed));
				}
				
				if (!blockedFeed){
					if (debug){
						logger.debug("Trying to get messages for LJ: " + url);
					}
					SyndFeed feed = RssUtils.buldFeed(url);
					
					if (feed == null){
						return;
					}
					@SuppressWarnings("unchecked")
					List<SyndEntryImpl> entries = (List<SyndEntryImpl>)feed.getEntries();
			    	if (debug){
						logger.debug("Got " + entries.size() + " messages for LJ: " + feed.getTitle());
					}
			    	((RssSource)rssSource).processMessages(feed, entries);
			    	rssSource.setLastUpdate(now);
				}
			} catch (ConnectException ce){
				rssSource.setLastFailure(now);
				connectionFail = true;
				logger.warn("Problem with connection " + url + " because of " + ce.getMessage());
			} catch (FileNotFoundException fnfe){
				rssSource.setBlocked(true);
				logger.warn("LJ url broken " + url);
			} catch (Exception e) {
				//if processor failed for some reason, we want to know it, but not to stop extraction process 
				rssSource.setLastFailure(now);
				logger.warn("Problem processing messages for LJ: " + url + " because of " + e.getMessage());
			}
			dataManager.saveExistingObject(rssSource);
		}
		logger.info("Processed " + i + " feeds.");
	}
}
