/**
 * 
 */
package net.mysocio.ui.data.objects.facebook;

import net.mysocio.ui.data.objects.UserUiMessage;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class FacebookUiMessage extends UserUiMessage {
	public static final String NAME = "FacebookMessage";
	public static final String CATEGORY = "Message";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public FacebookUiMessage() {
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}
	
	@Override
	public String getPageFile() {
		return "fbMessage.html";
	}
}
