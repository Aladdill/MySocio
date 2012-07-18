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
	private static IDataManager manager;

	public static void init(IDataManager dataManager){
		manager = dataManager;
	}

	public static IDataManager getDataManager() {
		return manager;
	}

	public static void closeDataConnection() {
	}
}
