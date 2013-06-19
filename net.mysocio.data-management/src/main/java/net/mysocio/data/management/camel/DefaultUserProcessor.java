/**
 * 
 */
package net.mysocio.data.management.camel;

import java.util.List;

import net.mysocio.data.AbstractUserMessagesProcessor;
import net.mysocio.data.AbstractUserProcessor;
import net.mysocio.data.IDataManager;
import net.mysocio.data.management.DataManagerFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Transient;

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

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		List<AbstractUserMessagesProcessor> userProcessors = dataManager.getUserProcessors(getUserId());
		for (AbstractUserMessagesProcessor userProcessor : userProcessors) {
			userProcessor.process();
		}
	}
}
