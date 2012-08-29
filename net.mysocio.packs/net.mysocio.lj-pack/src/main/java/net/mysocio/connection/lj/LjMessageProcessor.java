/**
 * 
 */
package net.mysocio.connection.lj;

import java.util.List;

import net.mysocio.connection.rss.RssMessageProcessor;
import net.mysocio.data.messages.lj.LjMessage;

import org.apache.camel.ProducerTemplate;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;

/**
 * @author Aladdin
 *
 */
public class LjMessageProcessor extends RssMessageProcessor {
	/* (non-Javadoc)
	 * @see net.mysocio.connection.rss.RssMessageProcessor#processMessage(org.apache.camel.ProducerTemplate, com.sun.syndication.feed.synd.SyndEntryImpl, net.mysocio.data.messages.rss.RssMessage)
	 */
	@Override
	protected void processMessages(SyndFeed feed, List<SyndEntryImpl> entries,
			ProducerTemplate producerTemplate) {
		SyndImage image = feed.getImage();
		for (SyndEntryImpl entry : entries) {
			LjMessage message = new LjMessage();
    		message.setUserpic(image.getUrl());
    		processMessage(producerTemplate, entry, message);
		}
	}
}
