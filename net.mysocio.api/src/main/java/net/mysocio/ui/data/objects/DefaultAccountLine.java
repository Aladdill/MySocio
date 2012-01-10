/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")


public class DefaultAccountLine extends UiObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6290318965037120058L;
	public static final String NAME = "DefaultAccount";
	public static final String CATEGORY = "AccountLine";
	
	public DefaultAccountLine(){
		super();
		setName(NAME);
		setCategory(CATEGORY);
		addTextLabel("account.primary");
	}
	@Override
	public String getPageFile() {
		return "defaultAccount.html";
	}
}
