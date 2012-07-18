/**
 * 
 */
package net.mysocio.connection.lj;

import java.util.List;

import net.mysocio.connection.rss.RssMessageProcessor;
import net.mysocio.data.SocioTag;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.lj.LjMessage;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;

/**
 * @author Aladdin
 *
 */
public class LjMessageProcessor extends RssMessageProcessor {
	static final Logger logger = LoggerFactory.getLogger(LjMessageProcessor.class);
	public void process(Exchange exchange) throws Exception {
		SyndFeed feed = (SyndFeed)exchange.getIn().getBody();
		List<SyndEntryImpl> entries = feed.getEntries();
    	if (logger.isDebugEnabled()){
			logger.debug("Got " + entries.size() + " messages for feed: " + feed.getTitle());
		}
    	ProducerTemplate producerTemplate = CamelContextManager.getProducerTemplate();
    	SyndImage image = feed.getImage();
    	for (SyndEntryImpl entry : entries) {
    		LjMessage message = new LjMessage();
    		String link = entry.getLink();
			message.setUniqueId(link);
    		message.setDate(entry.getPublishedDate().getTime());
    		message.setUserpic(image.getUrl());
    		String title = entry.getTitle();
    		if (title == null){
    			title = "";
    		}
			message.setTitle(title);
    		String text = entry.getDescription().getValue();
			message.setText(text);
			SocioTag tag = new SocioTag();
			tag.setIconType("lj.icon");
			tag.setValue(title);
			tag.setUserId(link);
			message.addTag(tag);
			addTagsToMessage(message);
    		if (logger.isDebugEnabled()){
    			logger.debug("Message title: " + title);
    			logger.debug("Message text: " + text);
    		}
    		MessagesManager.getInstance().storeMessage(message);
    		producerTemplate.sendBody(getTo(),message);
		}
	}
}
