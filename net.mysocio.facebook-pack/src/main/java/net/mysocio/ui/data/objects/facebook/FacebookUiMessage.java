/**
 * 
 */
package net.mysocio.ui.data.objects.facebook;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.ui.data.objects.UserUiMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
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
}
