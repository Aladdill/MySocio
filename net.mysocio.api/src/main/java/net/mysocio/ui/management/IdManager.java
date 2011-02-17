/**
 * 
 */
package net.mysocio.ui.management;

import java.util.UUID;


/**
 * @author Aladdin
 *
 */
public class IdManager {
	public static String getId(){
		return UUID.randomUUID().toString();
	}
}
