/**
 * 
 */
package net.mysocio.ui.data.objects.facebook;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class FacebookUiCheckinMessage extends FacebookUiMessage {
	public static final String NAME = "FacebookCheckinMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public FacebookUiCheckinMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbCheckinMessage.html";
	}
}
