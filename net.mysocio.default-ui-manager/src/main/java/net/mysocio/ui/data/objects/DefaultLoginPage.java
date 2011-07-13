/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.management.DefaultResourcesManager;

/**
 * @author Aladdin
 *
 */
public class DefaultLoginPage extends SiteBody {
	/**
	 * 
	 */
	private static final long serialVersionUID = 157601033356844407L;
	private static final String NAME = "DefaultLoginPage";
	/**
	 * 
	 */
	public DefaultLoginPage() {
		super();
		setName(NAME);
		addTextLabel("label.email");
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("loginPage.html");
	}
}
