/**
 * 
 */
package net.mysocio.ui.data.objects.facebook;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class FacebookUiVideoMessage extends FacebookUiMessage {
	public static final String NAME = "FacebookVideoMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public FacebookUiVideoMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbVideoMessage.html";
	}
}
