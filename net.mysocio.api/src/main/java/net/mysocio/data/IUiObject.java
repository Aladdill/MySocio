/**
 * 
 */
package net.mysocio.data;

import java.util.List;
import java.util.Map;

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