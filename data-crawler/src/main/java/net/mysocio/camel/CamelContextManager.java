/**
 * 
 */
package net.mysocio.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
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
	public static void initContext(JndiContext context){
		try {
			camelContext = new DefaultCamelContext(context);
			camelContext.start();
		} catch (Exception e) {
			logger.error("Failed to start camel context.", e);
		}
	}
	public static void addComponent(String name, Component component){
		camelContext.addComponent(name,component);
	}
	
	public static void addRoute(String from, Processor processor) throws Exception{
		GeneralRouteBuilder builder = new GeneralRouteBuilder(from, processor);
		camelContext.addRoutes(builder);
	}

	public static ProducerTemplate getProducerTemplate(){
		return camelContext.createProducerTemplate();
	}
	public static void stopContext(){
		try {
			camelContext.stop();
		} catch (Exception e) {
			logger.error("Failed to stop camel context.", e);
		}
	}
}
