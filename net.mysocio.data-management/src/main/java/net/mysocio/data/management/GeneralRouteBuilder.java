/**
 * 
 */
package net.mysocio.data.management;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
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
	private String to;
	private Processor processor;

	public GeneralRouteBuilder(String from, String to, Processor processor) {
		super();
		this.from = from;
		this.to = to;
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
			logger.debug("Creating route from: " + from + " to: " + to);
		}
		if (to == null) {
			from(from).process(processor);
		}else{
			from(from).process(processor).to(to);
		}
	}
}
