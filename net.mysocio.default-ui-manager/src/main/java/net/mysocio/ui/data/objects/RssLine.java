/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public class RssLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815419229256440884L;
	private static final String NAME = "RssSource";
	/**
	 * 
	 */
	public RssLine() {
		super();
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("RSS.html");
	}
}
