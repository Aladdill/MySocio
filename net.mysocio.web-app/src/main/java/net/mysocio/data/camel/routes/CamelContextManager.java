/**
 * 
 */
package net.mysocio.data.camel.routes;

import java.util.List;

import net.mysocio.connection.rss.RssSource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class CamelContextManager {
	static final Logger logger = LoggerFactory.getLogger(CamelContextManager.class);
	private static CamelContext camelContext = new DefaultCamelContext();
	public static void initContext(){
		try {
			camelContext.start();
		} catch (Exception e) {
			logger.error("Failed to start camel context.", e);
		}
	}
	public synchronized static void addRssRoutes(final List<RssSource> sources) throws Exception{
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				for (RssSource source : sources) {
		    		if (logger.isDebugEnabled()){
		    			logger.debug("Creating route for RSS feed on url" + source.getUrl());
		    		}
		    		from("rss:" + source.getUrl() + "?consumer.delay=2000").
		            bean(new RssMessagesRidingBean(source.getId()));
				}
			}
		});
	}
	public static void stopContext(){
		try {
			camelContext.stop();
		} catch (Exception e) {
			logger.error("Failed to stop camel context.", e);
		}
	}
}
