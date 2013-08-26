/**
 * 
 */
package net.mysocio.ui.data.objects.google;

import net.mysocio.ui.data.objects.UserUiMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class GoogleUiMessage extends UserUiMessage {
	public static final String NAME = "GoogleMessage";
	public static final String CATEGORY = "Message";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public GoogleUiMessage() {
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}
	
	@Override
	public String getPageFile() {
		return "googleMessage.html";
	}
}
