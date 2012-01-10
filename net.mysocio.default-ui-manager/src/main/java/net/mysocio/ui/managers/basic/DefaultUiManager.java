/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.ui.UiObject;
import net.mysocio.ui.data.objects.DefaultLoginPage;



/**
 * @author Aladdin
 *
 */
public class DefaultUiManager extends AbstractUiManager {
	
	public String getPage(String category, String name, SocioUser user) throws CorruptedDataException {
		String pageKey = category + name;
		String pageHTML = user.getPage(pageKey);
		if (pageHTML == null){
			IDataManager dataManager = DataManagerFactory.getDataManager(user);
			Map<String, UiObject> userObjects = dataManager.getUserUiObjects(user);
			if (userObjects == null){
				userObjects = Collections.emptyMap();
			}
			UiObject page = dataManager.getUiObject(category, name);
			if (page == null){
				throw new CorruptedDataException("Page: " + category + " " + name + " wasn't found.");
			}
			pageHTML = getUiObjectHtml(page , userObjects , new Locale(user.getLocale()));
			user.addPage(pageKey, pageHTML);
		}
		return pageHTML;
	}

	public String getLoginPage(Locale locale) throws CorruptedDataException {
		return getUiObjectHtml(new DefaultLoginPage(), Collections.EMPTY_MAP, locale);
	}
}
