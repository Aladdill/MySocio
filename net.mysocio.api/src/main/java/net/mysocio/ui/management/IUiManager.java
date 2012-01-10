/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public interface IUiManager {
	public abstract String getPage(String category, String name, SocioUser user) throws CorruptedDataException;
}
