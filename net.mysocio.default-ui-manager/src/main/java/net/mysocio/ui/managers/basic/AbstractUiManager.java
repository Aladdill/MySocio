/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.mysocio.data.IUiObject;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.management.IUiManager;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractUiManager implements IUiManager {

	public String getUiObjectHtml(IUiObject uiObject, Map<String, IUiObject> userObjects, Locale userLocale){
		String htmlTemplate = uiObject.getHtmlTemplate();
		List<String> innerTextLabels = uiObject.getInnerTextLabels();
		for (String textLable : innerTextLabels) {
			htmlTemplate = htmlTemplate.replace(textLable, getLocalizedString(textLable, userLocale));
		}
		Map<String, IUiObject> defaultInnerUiObjects = uiObject.getInnerUiObjects();
		Set<String> innerObjectsTags = defaultInnerUiObjects.keySet();
		for (String key : innerObjectsTags) {
			IUiObject innerObject = userObjects.get(key);
			if (innerObject == null){
				innerObject = defaultInnerUiObjects.get(key);
			}
			htmlTemplate = htmlTemplate.replace(key, getUiObjectHtml(innerObject, userObjects, userLocale));
		}
		return htmlTemplate;
	}
	
	public String getLocalizedString(String resource, Locale locale){
		return DefaultResourcesManager.getResource(locale, resource);
	}
}
