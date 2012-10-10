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
public class FacebookUiPhotoMessage extends FacebookUiMessage {
	public static final String NAME = "FacebookPhotoMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public FacebookUiPhotoMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbPhotoMessage.html";
	}
}
