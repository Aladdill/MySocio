/**
 * 
 */
package net.mysocio.ui.management;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.mysocio.data.IUiObject;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractUiManager implements IUiManager {

	public String getUiObjectHtml(IUiObject uiObject, Map<String, IUiObject> userObjects, Locale userLocale){
		String htmlTemplate = uiObject.getHtmlTemplate();
		List<String> innerTextLabels = uiObject.getInnerTextLabels();
		for (String textLable : innerTextLabels) {
			htmlTemplate.replace(textLable, getLocalizedString(textLable, userLocale));
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
	
	public String getLocalizedString(String string, Locale locale){
		return string;
	}
}
