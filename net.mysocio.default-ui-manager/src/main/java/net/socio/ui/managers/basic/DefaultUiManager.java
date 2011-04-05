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
import net.socio.ui.data.objects.DefaultLoginPage;



/**
 * @author Aladdin
 *
 */
public class DefaultUiManager extends AbstractUiManager {

	
	public String getPage(IUiObject page, SocioUser user) {
		Map<String, IUiObject> userObjects = DataManagerFactory.getDataManager().getUserUiObjects(user);
		return getUiObjectHtml(page, userObjects , new Locale(user.getLocale()));
	}

	public String getLoginPage(SocioUser user) {
		return getPage(new DefaultLoginPage(), user);
	}
}
