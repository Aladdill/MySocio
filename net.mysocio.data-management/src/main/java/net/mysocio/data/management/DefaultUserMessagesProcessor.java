/**
 * 
 */
package net.mysocio.data.management;

import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceAware;

import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.messages.GeneralMessage;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class DefaultUserMessagesProcessor extends UserRouteProcessor {
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserMessagesProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.setDetachAllOnCommit(true);
		SocioUser user = (SocioUser)dataManager.getObject(getUserId());
		GeneralMessage message = (GeneralMessage)exchange.getIn().getBody();
		if (logger.isDebugEnabled()){
			logger.debug("Got message " + message.getTitle() + " with uid " + message.getUniqueId());
		}
		Transaction transaction = dataManager.startTransaction();
		user.addUnreaddenMessage(message);
		dataManager.endTransaction(transaction);
	}
}
