/**
 * 
 */
package net.mysocio.ui.data.objects.vkontakte;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class VkontakteUiStatusMessage extends VkontakteUiMessage {
	public static final String NAME = "FacebookStatusMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public VkontakteUiStatusMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbStatusMessage.html";
	}
}
