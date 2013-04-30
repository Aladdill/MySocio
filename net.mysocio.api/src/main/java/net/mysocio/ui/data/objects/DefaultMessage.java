/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class DefaultMessage extends UiObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790983136433676389L;
	public static final String NAME = "DefaultMessage";
	public static final String CATEGORY = "Message";
	
	public DefaultMessage(){
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}
	@Override
	public String getPageFile() {
		return "message.html";
	}
}
