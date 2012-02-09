/**
 * 
 */
package net.mysocio.data.management;

import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceAware;

import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class MarkMessageReaddenProcessor extends UserRouteProcessor {
	private static final Logger logger = LoggerFactory.getLogger(MarkMessageReaddenProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.setDetachAllOnCommit(true);
		SocioUser user = (SocioUser)dataManager.getObject(getUserId());
		String id = (String)exchange.getIn().getBody();
		if (logger.isDebugEnabled()){
			logger.debug("Marking readen message with id " + id);
		}
		Transaction transaction = dataManager.startTransaction();
		user.setMessageReadden(id);
		dataManager.endTransaction(transaction);
	}
}
