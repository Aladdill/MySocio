/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
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
