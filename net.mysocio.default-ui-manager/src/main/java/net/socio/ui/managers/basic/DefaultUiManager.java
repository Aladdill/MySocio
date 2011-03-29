/**
 * 
 */
package net.socio.ui.managers.basic;

import java.util.Locale;
import java.util.Map;

import net.mysocio.data.IUiObject;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.AbstractUiManager;



/**
 * @author Aladdin
 *
 */
public class DefaultUiManager extends AbstractUiManager {
	@Override
	public String getStartingPage(SocioUser user){
		Map<String, IUiObject> userObjects = DataManagerFactory.getDataManager().getUserUiObjects(user);
		return getUiObjectHtml(new DefaultPage(), userObjects , new Locale(user.getLocale()));
	}
}
