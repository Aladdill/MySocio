/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioPair;
import net.mysocio.data.management.DataManagerFactory;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
@Entity
public class MarkMessageReaddenProcessor extends AbstractProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491823008113289073L;
	@Transient
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
