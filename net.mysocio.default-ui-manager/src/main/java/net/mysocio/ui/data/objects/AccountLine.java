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
public class AccountLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7232276926453378696L;
	private static final String NAME = "Account";
	
	public AccountLine(){
		super();
		setName(NAME);
		addTextLabel("account.make.primary");
		addTextLabel("account.remove");
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("account.html");
	}
}
