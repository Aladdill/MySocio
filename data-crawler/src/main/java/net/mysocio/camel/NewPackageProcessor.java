/**
 * 
 */
package net.mysocio.camel;

import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.RoutePackage;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
@Entity
public class NewPackageProcessor extends AbstractProcessor {
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(NewPackageProcessor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 9211736669341986421L;

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		RoutePackage routePackage = (RoutePackage)exchange.getIn().getBody();
		//We can't sent package to nowhere
		if (routePackage.getTo() == null){
			logger.debug("Empty package discovered");
			return;
		}
		ProducerTemplate producerTemplate = CamelContextManager.getProducerTemplate();
		producerTemplate.sendBody(routePackage.getTo(), routePackage.getObject());
	}
}
