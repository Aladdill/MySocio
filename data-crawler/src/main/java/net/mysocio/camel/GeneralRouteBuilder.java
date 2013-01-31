/**
 * 
 */
package net.mysocio.camel;

import net.mysocio.data.SocioRoute;

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
	private String to;
	private Processor processor;
	private Long delay;
	private boolean autoStartup;

	private String routeId;


	public GeneralRouteBuilder(SocioRoute route) {
		super();
		this.from = route.getFrom();
		this.to = route.getTo();
		this.processor = route.getProcessor();
		this.delay = route.getDelay();
		this.autoStartup = route.isAutoStartup();
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
		RouteDefinition routeDefinition = from(from).autoStartup(autoStartup).process(processor).delayer(delay);;
		if (to != null) {
			routeDefinition = routeDefinition.to(to);
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
	
	public boolean isAutoStartup() {
		return autoStartup;
	}

	public void setAutoStartup(boolean autoStartup) {
		this.autoStartup = autoStartup;
	}
}
