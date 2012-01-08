/**
 * 
 */
package net.mysocio.connection.rss;

import java.util.List;

import net.mysocio.data.SocioTag;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.rss.RssMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Aladdin
 *
 */
public class RssMessageProcessor implements Processor {
	static final Logger logger = LoggerFactory.getLogger(RssMessageProcessor.class);
	private String to;
	
	public void process(Exchange exchange) throws Exception {
		SyndFeed feed = (SyndFeed)exchange.getIn().getBody();
		List<SyndEntryImpl> entries = feed.getEntries();
    	if (logger.isDebugEnabled()){
			logger.debug("Got " + entries.size() + " messages for feed: " + feed.getTitle());
		}
    	ProducerTemplate producerTemplate = CamelContextManager.getProducerTemplate();
    	for (SyndEntryImpl entry : entries) {
    		RssMessage message = new RssMessage();
    		message.setUniqueId(entry.getLink());
    		message.setDate(entry.getPublishedDate().getTime());
    		String title = entry.getTitle();
			message.setTitle(title);
    		String text = entry.getDescription().getValue();
			message.setText(text);
    		if (logger.isDebugEnabled()){
    			logger.debug("Message title: " + title);
    			logger.debug("Message text: " + text);
    		}
    		MessagesManager.getInstance().storeMessage(message);
    		SocioTag tag = new SocioTag();
    		tag.setIconType("rss.icon");
    		tag.setValue(message.getTitle());
    		tag.setUniqueId(feed.getUri());
    		message.addTag(tag);
    		SocioTag tag1 = new SocioTag();
    		tag1.setIconType("rss.icon");
    		tag1.setValue("rss.tag");
    		tag1.setUniqueId("rss.tag");
    		message.addTag(tag1);
    		producerTemplate.sendBody(to,message);
		}
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
