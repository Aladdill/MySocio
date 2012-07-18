/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.CorruptedDataException;

/**
 * @author Aladdin
 *
 */
public interface IUiManager {
	public abstract String getPage(String category, String name, String userId) throws CorruptedDataException;
}
