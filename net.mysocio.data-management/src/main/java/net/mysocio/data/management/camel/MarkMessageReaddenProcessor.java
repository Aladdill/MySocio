/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioPair;
import net.mysocio.data.management.DataManagerFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class MarkMessageReaddenProcessor implements Processor {
	public static final String ACTIVEMQ_READEN_MESSAGE = "activemq:readenMessage";
	private static final Logger logger = LoggerFactory.getLogger(MarkMessageReaddenProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioPair pair = (SocioPair)exchange.getIn().getBody();
		String userId = pair.getValue1();
		String messageId = pair.getValue2();
		if (logger.isDebugEnabled()){
			logger.debug("Marking readen message with id " + messageId + " for user id " + userId);
		}
		dataManager.setMessageReadden(userId, messageId);
	}
}
