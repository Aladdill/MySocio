/**
 * 
 */
package net.mysocio.data.ui;

import java.util.List;
import java.util.Map;

import net.mysocio.data.INamedObject;

/**
 * @author Aladdin
 *
 */
public interface IUiObject extends INamedObject {
	public String getCategory();
	public Map<String, IUiObject> getInnerUiObjects();
	public List<String> getInnerTextLabels();
	public String getHtmlTemplate();
}
