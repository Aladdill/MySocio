/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.ui.UiObject;
import net.mysocio.ui.management.IUiManager;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractUiManager implements IUiManager {

	public String getUiObjectHtml(UiObject uiObject, Map<String, UiObject> userObjects, Locale userLocale) throws CorruptedDataException{
		String htmlTemplate = uiObject.getHtmlTemplate();
		if (htmlTemplate == null || htmlTemplate.isEmpty()){
			htmlTemplate = DefaultResourcesManager.getPage(uiObject.getPageFile());
			if (htmlTemplate == null || htmlTemplate.isEmpty()){
				throw new CorruptedDataException("Page: " + uiObject.getName() + " wasn't found.");
			}
		}
		List<String> innerTextLabels = uiObject.getInnerTextLabels();
		for (String textLable : innerTextLabels) {
			htmlTemplate = htmlTemplate.replace(textLable, getLocalizedString(textLable, userLocale));
		}
		Map<String, UiObject> defaultInnerUiObjects = uiObject.getInnerUiObjects();
		Set<String> innerObjectsTags = defaultInnerUiObjects.keySet();
		for (String key : innerObjectsTags) {
			UiObject innerObject = userObjects.get(key);
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
