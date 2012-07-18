/**
 * 
 */
package net.mysocio.data.management;

import net.mysocio.data.IDataManager;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class MarkMessageReaddenProcessor extends UserRouteProcessor {
	private static final Logger logger = LoggerFactory.getLogger(MarkMessageReaddenProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String messageId = (String)exchange.getIn().getBody();
		if (logger.isDebugEnabled()){
			logger.debug("Marking readen message with id " + messageId);
		}
		dataManager.setMessageReadden(messageId, getUserId());
	}
}
