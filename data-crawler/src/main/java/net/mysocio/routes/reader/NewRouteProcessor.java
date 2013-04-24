/**
 * 
 */
package net.mysocio.routes.reader;

import net.mysocio.camel.CamelContextManager;
import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioRoute;
import net.mysocio.data.TempRoute;
import net.mysocio.data.management.DataManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public class NewRouteProcessor extends CappedCollectionProcessor {
	private static final Logger logger = LoggerFactory.getLogger(NewRouteProcessor.class);
	public long process(Object tempRouteObject) throws Exception {
		TempRoute tempRoute = getObject(TempRoute.class, tempRouteObject);
		IDataManager dataManager = DataManagerFactory.getDataManager();
		//We want to ignore routes from nowhere to nowhere
		String from = tempRoute.getFrom();
		String to = tempRoute.getTo();
		if (from == null && to == null){
			logger.debug("Empty route discovered");
			return tempRoute.getCreationDate(); 
		}
		AbstractProcessor processor = tempRoute.getProcessor();
		if (!dataManager.isRouteExist(from, processor)){
			SocioRoute route = new SocioRoute();
			route.setDelay(tempRoute.getDelay());
			route.setFrom(from);
			route.setTo(to);
			route.setProcessor(processor);
			dataManager.saveObject(route);
			route.setCamelRouteId(CamelContextManager.addRoute(route));
			dataManager.saveObject(route);
		}
		return tempRoute.getCreationDate();
	}
}
