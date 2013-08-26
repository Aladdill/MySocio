/**
 * 
 */
package net.mysocio.connection.rss;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.management.DataManagerFactory;
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

	public void createProcessor(String userId) throws Exception {
		String url = getUrl();
		if (logger.isDebugEnabled()){
			logger.debug("Creating route for RSS feed on url " + url);
		}
		RssMessageProcessor processor = new RssMessageProcessor();
		processor.setUrl(url);
		processor.setUserId(userId);
		DataManagerFactory.getDataManager().saveProcessor(processor, "url", getUrl());
	}

	@Override
	public void removeProcessor(String userId) throws Exception {
		DataManagerFactory.getDataManager().deleteUserProcessorByField(RssMessageProcessor.class, "url", getUrl(), userId);		
	}
}
