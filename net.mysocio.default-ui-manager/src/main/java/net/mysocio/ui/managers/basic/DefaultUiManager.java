/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import net.mysocio.data.IUiObject;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.data.objects.DefaultLoginPage;
import net.mysocio.ui.management.AbstractUiManager;



/**
 * @author Aladdin
 *
 */
public class DefaultUiManager extends AbstractUiManager {
	public String getPage(IUiObject page, SocioUser user) {
		Map<String, IUiObject> userObjects = DataManagerFactory.getDataManager().getUserUiObjects(user);
		return getUiObjectHtml(page, userObjects , new Locale(user.getLocale()));
	}

	public String getLoginPage(Locale locale) {
		return getUiObjectHtml(new DefaultLoginPage(), Collections.EMPTY_MAP, locale);
	}
}
