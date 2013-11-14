/**
 * 
 */
package net.mysocio.data.management.camel;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mysocio.data.AbstractUserMessagesProcessor;
import net.mysocio.data.AbstractUserProcessor;
import net.mysocio.data.IDataManager;
import net.mysocio.data.management.DataManagerFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
public class DefaultUserProcessor extends AbstractUserProcessor implements Processor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 178995248777936666L;
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserProcessor.class);
	@Transient
	private Map<String, Long> failures = new HashMap<String, Long>(); 

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange){
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<AbstractUserMessagesProcessor> userProcessors = dataManager.getUserProcessors(getUserId());
		for (AbstractUserMessagesProcessor userProcessor : userProcessors) {
			String processorId = userProcessor.getId().toString();
			Long lastFailure = failures.get(processorId);
			logger.debug("Starting processor with id: " + userProcessor.getId() + " for routeId : " + exchange.getFromRouteId());
			long now = System.currentTimeMillis();
			try {
				//if connection failed, we want to retry only after 15 minutes
				if (lastFailure == null || (now - lastFailure) > 1000*60*15){
					userProcessor.process();
				}
			} catch (ConnectException ce){
				failures.put(processorId, now);
				logger.warn("Problem with connection " + userProcessor.getId() + " because of " + ce.getMessage());
			} catch (Exception e) {
				//if processor failed for some reason, we want to know it, but not to stop extraction process 
				failures.put(processorId, now);
				logger.warn("Problem processing messages for processor with id: " + userProcessor.getId(), e);
			}
		}
	}
}
