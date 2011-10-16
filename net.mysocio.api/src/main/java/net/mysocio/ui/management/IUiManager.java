/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.SocioUser;
import net.mysocio.data.ui.IUiObject;

/**
 * @author Aladdin
 *
 */
public interface IUiManager {
	public abstract String getPage(IUiObject uiObject, SocioUser user);
}
