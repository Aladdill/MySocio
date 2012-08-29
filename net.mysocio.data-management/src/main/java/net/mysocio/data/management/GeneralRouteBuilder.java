/**
 * 
 */
package net.mysocio.data.management;

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
	private final long DEFAULT_ROUTE_DELAY = 60000;
	private String from;
	private String to;
	private Processor processor;
	private Long delay = DEFAULT_ROUTE_DELAY;
	private String routeId;

	public GeneralRouteBuilder(String from, String to, Processor processor) {
		super();
		this.from = from;
		this.to = to;
		this.processor = processor;
	}

	public GeneralRouteBuilder(String from, String to, Processor processor,	Long delay) {
		super();
		this.from = from;
		this.to = to;
		this.processor = processor;
		this.delay = delay;
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
		RouteDefinition routeDefinition = null;
		if (to == null) {
			if (delay != 0){
				routeDefinition = from(from).delayer(delay).process(processor);
			}else{
				routeDefinition = from(from).process(processor);
			}
		}else{
			if (delay != 0){
				from(from).delayer(delay).process(processor).to(to);
			}else{
				routeDefinition = from(from).process(processor).to(to);
			}
		}
		
		this.routeId = routeDefinition.getId();
	}

	/**
	 * @return the delay
	 */
	public Long getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(Long delay) {
		this.delay = delay;
	}

	/**
	 * @return the routeId
	 */
	public String getRouteId() {
		return routeId;
	}
}
