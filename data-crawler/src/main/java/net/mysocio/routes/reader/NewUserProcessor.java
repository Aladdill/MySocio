/**
 * 
 */
package net.mysocio.routes.reader;

import net.mysocio.camel.CamelContextManager;
import net.mysocio.data.IDataManager;
import net.mysocio.data.TempProcessor;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.camel.DefaultUserProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public class NewUserProcessor extends CappedCollectionProcessor {
	private static final Logger logger = LoggerFactory.getLogger(NewUserProcessor.class);
	public long process(Object tempRouteObject) throws Exception {
		TempProcessor tempProcessor = getObject(TempProcessor.class, tempRouteObject);
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String userId = tempProcessor.getUserId();
		if (!dataManager.isProcessorExist(userId)){
			DefaultUserProcessor processor = new DefaultUserProcessor();
			processor.setUserId(userId);
			logger.debug("New user processor was registered for user with id: " + userId);
			dataManager.saveObject(processor);
			CamelContextManager.addRoute("timer://" + processor.getUserId() + "?fixedRate=true&period=60s", processor);
		}
		return tempProcessor.getCreationDate();
	}
}
