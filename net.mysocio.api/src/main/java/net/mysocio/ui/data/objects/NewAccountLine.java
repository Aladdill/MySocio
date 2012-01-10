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


public class NewAccountLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 801875885810187718L;
	public static final String NAME = "NewAccount";
	public static final String CATEGORY = "AccountLine";
	
	public NewAccountLine(){
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}
	@Override
	public String getPageFile() {
		return "newAccount.html";
	}
}
