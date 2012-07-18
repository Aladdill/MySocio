/**
 * 
 */
package net.mysocio.data.management;

import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.messages.UnreaddenMessage;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class DefaultUserMessagesProcessor extends UserRouteProcessor {
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserMessagesProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		UnreaddenMessage ureaddenMessage = (UnreaddenMessage)exchange.getIn().getBody();
		if (logger.isDebugEnabled()){
			logger.debug("Got message with uid " + ureaddenMessage.getMessageId());
		}
		String userId = getUserId();
		ureaddenMessage.setUserId(userId);
		SocioTag tag = dataManager.getTag(userId,ureaddenMessage.getTag().getValue());
		if (tag == null){
			tag = ureaddenMessage.getTag();
			tag.setUserId(userId);
			dataManager.saveObject(tag);
			ureaddenMessage.setTag(tag);
		}
		dataManager.saveObject(ureaddenMessage);
	}
}
