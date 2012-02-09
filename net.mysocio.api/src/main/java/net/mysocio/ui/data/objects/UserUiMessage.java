/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
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
