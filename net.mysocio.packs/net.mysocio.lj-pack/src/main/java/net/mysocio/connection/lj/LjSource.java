/**
 * 
 */
package net.mysocio.connection.lj;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.lj.LjMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public class LjSource extends RssSource {
	static final Logger logger = LoggerFactory.getLogger(LjSource.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -559758680101816312L;
	
	public Class<?> getMessageClass() {
		return LjMessage.class;
	}
	
	public void createRoute(String to) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Creating route for LJ feed on url" + getUrl());
		}
		LjMessageProcessor processor = new LjMessageProcessor();
		processor.setTo(to);
		processor.addTags(getTags());
		DataManagerFactory.getDataManager().createRoute("rss:" + getUrl() + "?consumer.delay=2000", processor, null, 0l);
	}
}
