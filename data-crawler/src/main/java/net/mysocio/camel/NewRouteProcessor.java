/**
 * 
 */
package net.mysocio.camel;

import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.SocioRoute;
import net.mysocio.data.TempRoute;
import net.mysocio.data.management.DataManagerFactory;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
@Entity
public class NewRouteProcessor extends AbstractProcessor {
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(NewRouteProcessor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 6648926043159830239L;
	public void process(Exchange exchange) throws Exception{
		TempRoute tempRoute = (TempRoute)exchange.getIn().getBody();
		//We want to ignore routes from nowhere to nowhere 
		if (tempRoute.getFrom() == null && tempRoute.getTo() == null){
			logger.debug("Empty route discovered");
			return;
		}
		SocioRoute route = new SocioRoute();
		route.setDelay(tempRoute.getDelay());
		route.setFrom(tempRoute.getFrom());
		route.setTo(tempRoute.getTo());
		route.setProcessor(tempRoute.getProcessor());
		DataManagerFactory.getDataManager().saveObject(route);
		CamelContextManager.addRoute(route);
	}
}
