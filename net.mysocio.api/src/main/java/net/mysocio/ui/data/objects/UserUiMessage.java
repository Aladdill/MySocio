/**
 * 
 */
package net.mysocio.ui.data.objects;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class UserUiMessage extends DefaultMessage {
	public static final String NAME = "UserMessage";
	public static final String CATEGORY = "Message";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;
	
	public UserUiMessage() {
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}

	@Override
	public String getPageFile() {
		return "userMessage.html";
	}
}
