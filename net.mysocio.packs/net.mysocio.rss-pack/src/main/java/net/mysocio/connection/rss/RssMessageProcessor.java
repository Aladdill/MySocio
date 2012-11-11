/**
 * 
 */
package net.mysocio.connection.rss;

import java.util.List;

import net.mysocio.data.SocioTag;
import net.mysocio.data.management.AbstractMessageProcessor;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.rss.RssMessage;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Aladdin
 *
 */
public class RssMessageProcessor extends AbstractMessageProcessor {
	static final Logger logger = LoggerFactory.getLogger(RssMessageProcessor.class);
	
	public void process(Exchange exchange) throws Exception {
		SyndFeed feed = (SyndFeed)exchange.getIn().getBody();
		List<SyndEntryImpl> entries = feed.getEntries();
    	if (logger.isDebugEnabled()){
			logger.debug("Got " + entries.size() + " messages for feed: " + feed.getTitle());
		}
    	ProducerTemplate producerTemplate = CamelContextManager.getProducerTemplate();
    	processMessages(feed, entries, producerTemplate);
	}

	protected void processMessages(SyndFeed feed, List<SyndEntryImpl> entries,
			ProducerTemplate producerTemplate) {
		for (SyndEntryImpl entry : entries) {
    		RssMessage message = new RssMessage();
    		processMessage(producerTemplate, entry, message);
		}
	}

	protected void processMessage(ProducerTemplate producerTemplate, SyndEntryImpl entry, RssMessage message) {
		message.setLink(entry.getLink());
		message.setDate(entry.getPublishedDate().getTime());
		String title = entry.getTitle();
		if (title == null){
			title = "";
		}
		message.setTitle(title);
		String text = entry.getDescription().getValue();
		message.setText(text);
		
		if (logger.isDebugEnabled()){
			logger.debug("Message title: " + title);
			logger.debug("Message text: " + text);
		}
		try {
			MessagesManager.getInstance().storeMessage(message);
		} catch (Exception e) {
			//if it's duplicate message - we ignore it
			return;
		}
		for (SocioTag tag : tags) {
			addMessageForTag(producerTemplate, message, tag);
		}
	}
}
