/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.IUiObject;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public interface IUiManager {
	public abstract String getPage(IUiObject uiObject, SocioUser user);
}
