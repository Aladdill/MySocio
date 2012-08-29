/**
 * 
 */
package net.mysocio.connection.rss;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.messages.rss.RssMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public class RssSource extends Source {
	static final Logger logger = LoggerFactory.getLogger(RssSource.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3623303809928356829L;
	
	public Class<?> getMessageClass() {
		return RssMessage.class;
	}

	public void createRoute(String to) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Creating route for RSS feed on url" + getUrl());
		}
		RssMessageProcessor processor = new RssMessageProcessor();
		processor.setTo(to);
		processor.addTags(getTags());
		CamelContextManager.addRoute("rss:" + getUrl() + "?consumer.delay=2000", processor , null);
	}
}
