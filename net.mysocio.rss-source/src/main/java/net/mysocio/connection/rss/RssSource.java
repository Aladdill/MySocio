/**
 * 
 */
package net.mysocio.connection.rss;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.messages.rss.RssMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
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
		SocioTag tag = new SocioTag();
		tag.setIconType("rss.icon");
		tag.setValue(getName());
		tag.setUniqueId(getUrl());
		processor.addTag(tag);
		SocioTag tag1 = new SocioTag();
		tag1.setIconType("rss.icon");
		tag1.setValue("rss.tag");
		tag1.setUniqueId("rss.tag");
		processor.addTag(tag1);
		CamelContextManager.addRoute("rss:" + getUrl() + "?consumer.delay=2000", processor , null);
	}
}
