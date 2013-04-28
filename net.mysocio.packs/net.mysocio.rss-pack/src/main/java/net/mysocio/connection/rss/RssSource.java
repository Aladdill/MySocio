/**
 * 
 */
package net.mysocio.connection.rss;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.rss.RssMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Entity;

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
		String url = getUrl();
		if (logger.isDebugEnabled()){
			logger.debug("Creating route for RSS feed on url " + url);
		}
		RssMessageProcessor processor = new RssMessageProcessor();
		processor.setTo(to);
		processor.setTag(url);
		String separator = "?";
		if (url.contains("?")){
			separator = "&";
		}
		DataManagerFactory.getDataManager().createRoute("rss:" + url + separator + "consumer.delay=60000&mysociouserId=" + to.substring(9, 33), processor, 0l);
	}

	@Override
	public void removeRoute(String userId) throws Exception {
		String url = getUrl();
		String separator = "?";
		if (url.contains("?")){
			separator = "&";
		}
		DataManagerFactory.getDataManager().removeRoute("rss:" + url + separator + "consumer.delay=60000&mysociouserId=" + userId, userId);		
	}
}
