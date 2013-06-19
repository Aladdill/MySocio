/**
 * 
 */
package net.mysocio.connection.lj;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.lj.LjMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Entity;

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
	
	public void createProcessor(String userId) throws Exception {
		String url = getUrl();
		if (logger.isDebugEnabled()){
			logger.debug("Creating route for LJ feed on url" + url);
		}
		LjMessageProcessor processor = new LjMessageProcessor();
		processor.setUrl(url);
		processor.setUserId(userId);
		DataManagerFactory.getDataManager().saveProcessor(processor, "url", getUrl());
	}
	
	@Override
	public void removeProcessor(String userId) throws Exception {
		DataManagerFactory.getDataManager().deleteUserProcessorByField(LjMessageProcessor.class, "url", getUrl(), userId);		
	}
}
