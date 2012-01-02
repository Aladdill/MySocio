/**
 * 
 */
package net.mysocio.data.management;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public class DataManagerFactory {
	private static PersistenceManagerFactory pmf;
	public static void init(String database){
		if (pmf == null) {
			// Create a PersistenceManagerFactory for this datastore
			pmf = JDOHelper.getPersistenceManagerFactory(database);
		}
	}
	public static IDataManager getDataManager(){
		return new JdoDataManager(pmf.getPersistenceManager());
	}
	public static IDataManager getDataManager(SocioUser user){
		PersistenceManager persistenceManager = JDOHelper.getPersistenceManager(user);
		if (persistenceManager == null){
			persistenceManager = pmf.getPersistenceManager();
		}
		return new JdoDataManager(persistenceManager);
	}
	
	public static void closeDataConnection() {
		if (pmf != null) {
			pmf.close();
			pmf = null;
		}
	}
}
