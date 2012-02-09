/**
 * 
 */
package net.mysocio.connection.lj;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.SocioTag;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.messages.lj.LjMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
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
		List<SocioTag> tags = getTags();
		for (SocioTag tag : tags) {
			processor.addTag(tag);
		}
		CamelContextManager.addRoute("rss:" + getUrl() + "?consumer.delay=2000", processor , null);
	}
}
