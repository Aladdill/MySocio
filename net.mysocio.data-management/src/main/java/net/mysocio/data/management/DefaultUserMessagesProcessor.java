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
import org.apache.camel.Processor;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class DefaultUserMessagesProcessor implements Processor {
	private String userId; 

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.setDetachAllOnCommit(true);
		SocioUser user = (SocioUser)dataManager.getObject(userId);
		GeneralMessage message = (GeneralMessage)exchange.getIn().getBody();
		Transaction transaction = dataManager.startTransaction();
		user.addUnreaddenMessage(message);
		dataManager.endTransaction(transaction);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
