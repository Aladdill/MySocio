/**
 * 
 */
package net.mysocio.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
public class GeneralRouteBuilder extends RouteBuilder {
	static final Logger logger = LoggerFactory
			.getLogger(GeneralRouteBuilder.class);
	private String from;
	private Processor processor;


	public GeneralRouteBuilder(String from, Processor processor) {
		super();
		this.from = from;
		this.processor = processor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating route from " + from);
		}
		RouteDefinition routeDefinition = from(from).autoStartup(true).process(processor);
	}
}
