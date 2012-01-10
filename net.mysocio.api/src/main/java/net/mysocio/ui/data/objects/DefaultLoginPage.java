/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")


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
		addTextLabel("login.page.baloon.text");
		addTextLabel("login.page.promo.general.text");
		addTextLabel("login.page.promo.about.header.text");
		addTextLabel("login.page.promo.about.text");
		addTextLabel("login.page.promo.your.look.header.text");
		addTextLabel("login.page.promo.your.look.text");
	}
	@Override
	public String getPageFile() {
		return "loginPage.html";
	}
}
