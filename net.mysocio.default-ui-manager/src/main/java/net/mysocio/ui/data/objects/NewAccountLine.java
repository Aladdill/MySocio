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
public class NewAccountLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 801875885810187718L;
	private static final String NAME = "NewAccount";
	
	public NewAccountLine(){
		super();
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("newAccount.html");
	}
}
