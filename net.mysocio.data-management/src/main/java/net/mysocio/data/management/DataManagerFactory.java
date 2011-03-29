/**
 * 
 */
package net.mysocio.data.management;

/**
 * @author Aladdin
 *
 */
public class DataManagerFactory {
	public static IDataManager getDataManager(){
		return JdoDataManager.getInstance();
	}
}
