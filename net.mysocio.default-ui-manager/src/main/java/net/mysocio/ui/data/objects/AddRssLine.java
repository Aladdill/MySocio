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
public class AddRssLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815419229256440884L;
	private static final String NAME = "NewRss";
	/**
	 * 
	 */
	public AddRssLine() {
		super();
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("addRSS.html");
	}
}
