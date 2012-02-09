/**
 * 
 */
package net.mysocio.data.management;

import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceAware;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class SourcesManager {
	public static void addSourceToUser(SocioUser user, Source source) throws Exception{
		IDataManager dataManager = DataManagerFactory.getDataManager(user);
		Source savedSource = (Source)dataManager.createSource(source);
		source.createRoute("activemq:" + user.getId()  + ".newMessage");
		Transaction transaction = dataManager.startTransaction();
		user.addSource(savedSource);
		dataManager.endTransaction(transaction);
	}
}
