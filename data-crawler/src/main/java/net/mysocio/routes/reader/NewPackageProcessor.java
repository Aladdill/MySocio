/**
 * 
 */
package net.mysocio.routes.reader;

import net.mysocio.camel.CamelContextManager;
import net.mysocio.data.RoutePackage;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public class NewPackageProcessor extends CappedCollectionProcessor {
	private static final Logger logger = LoggerFactory.getLogger(NewPackageProcessor.class);

	@Override
	public long process(Object packageObject) throws Exception {
		RoutePackage routePackage = getObject(RoutePackage.class, packageObject);
		//We can't sent package to nowhere
		if (routePackage.getTo() == null){
			logger.debug("Package to unknown destination discovered");
			return routePackage.getCreationDate();
		}
		ProducerTemplate producerTemplate = CamelContextManager.getProducerTemplate();
		producerTemplate.sendBody(routePackage.getTo(), routePackage.getObject());
		return routePackage.getCreationDate();
	}
}
