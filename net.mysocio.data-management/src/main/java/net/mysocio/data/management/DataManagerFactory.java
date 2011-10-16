/**
 * 
 */
package net.mysocio.data.management;

import net.mysocio.data.IDataManager;

/**
 * @author Aladdin
 *
 */
public class DataManagerFactory {
	public static IDataManager getDataManager(){
		return JdoDataManager.getInstance();
	}
}
