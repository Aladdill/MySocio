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
public class FacebookUiLinkMessage extends FacebookUiMessage {
	public static final String NAME = "FacebookLinkMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public FacebookUiLinkMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbLinkMessage.html";
	}
}
