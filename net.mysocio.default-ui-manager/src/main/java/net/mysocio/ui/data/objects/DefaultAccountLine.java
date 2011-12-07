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
public class DefaultAccountLine extends UiObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6290318965037120058L;
	private static final String NAME = "DefaultAccount";
	
	public DefaultAccountLine(){
		super();
		setName(NAME);
		addTextLabel("account.primary");
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("defaultAccount.html");
	}
}
