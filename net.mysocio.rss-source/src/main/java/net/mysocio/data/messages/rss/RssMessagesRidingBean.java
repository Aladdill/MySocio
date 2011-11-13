package net.mysocio.data.messages.rss;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.management.IRssMessagesRidingBean;
import net.mysocio.data.management.MessagesManager;
import net.mysocio.data.messages.IMessage;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;

public class RssMessagesRidingBean implements IRssMessagesRidingBean {
	static final Logger logger = LoggerFactory.getLogger(RssMessagesRidingBean.class);
	private String id;
	private String url;

	public void readMessages(@Body SyndFeed feed) {
		
    	List<SyndEntryImpl> entries = feed.getEntries();
    	List<IMessage> messages = new ArrayList<IMessage>();
    	if (logger.isDebugEnabled()){
			logger.debug("Got " + entries.size() + " messages for feed: " + feed.getUri());
		}
    	for (SyndEntryImpl entry : entries) {
    		RssMessage message = new RssMessage();
    		message.setUniqueId(entry.getLink());
    		message.setSourceId(id);
    		message.setDate(entry.getPublishedDate().getTime());
    		String title = entry.getTitle();
			message.setTitle(title);
    		String text = entry.getDescription().getValue();
			message.setText(text);
    		if (logger.isDebugEnabled()){
    			logger.debug("Message title: " + title);
    			logger.debug("Message text: " + text);
    		}
    		messages.add(message);
		}
    	MessagesManager.getInstance().storeMessages(messages);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}