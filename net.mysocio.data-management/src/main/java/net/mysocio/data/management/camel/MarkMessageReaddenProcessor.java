/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.IDataManager;
import net.mysocio.data.StringWrapper;
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
public class MarkMessageReaddenProcessor extends UserRouteProcessor {
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
		String messageId = ((StringWrapper)exchange.getIn().getBody()).getString();
		if (logger.isDebugEnabled()){
			logger.debug("Marking readen message with id " + messageId);
		}
		dataManager.setMessageReadden(messageId);
	}
}
