/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.IDataManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
public class DefaultUserMessagesProcessor extends UserRouteProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 178995248777936666L;
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserMessagesProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		UnreaddenMessage ureaddenMessage = (UnreaddenMessage)exchange.getIn().getBody();
		GeneralMessage message = ureaddenMessage.getMessage();
		if (logger.isDebugEnabled()){
			logger.debug("Got message with uid " + message.getUniqueFieldValue().toString());
		}
		String userId = getUserId();
		
		if (dataManager.isNewMessage(userId, message)){
			ureaddenMessage.setUserId(userId);
			dataManager.saveObject(ureaddenMessage);
		}
	}
}
