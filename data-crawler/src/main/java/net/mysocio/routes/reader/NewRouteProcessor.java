/**
 * 
 */
package net.mysocio.routes.reader;

import net.mysocio.camel.CamelContextManager;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioRoute;
import net.mysocio.data.TempRoute;
import net.mysocio.data.management.DataManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public class NewRouteProcessor extends CappedCollectionProcessor {
	private static final Logger logger = LoggerFactory.getLogger(NewRouteProcessor.class);
	public long process(Object tempRouteObject) throws Exception {
		TempRoute tempRoute = getObject(TempRoute.class, tempRouteObject);
		//We want to ignore routes from nowhere to nowhere 
		if (tempRoute.getFrom() == null && tempRoute.getTo() == null){
			logger.debug("Empty route discovered");
			return tempRoute.getCreationDate(); 
		}
		SocioRoute route = new SocioRoute();
		route.setDelay(tempRoute.getDelay());
		route.setFrom(tempRoute.getFrom());
		route.setTo(tempRoute.getTo());
		route.setProcessor(tempRoute.getProcessor());
		IDataManager dataManager = DataManagerFactory.getDataManager();
		dataManager.saveObject(route);
		route.setCamelRouteId(CamelContextManager.addRoute(route));
		dataManager.saveObject(route);
		return tempRoute.getCreationDate();
	}
}
