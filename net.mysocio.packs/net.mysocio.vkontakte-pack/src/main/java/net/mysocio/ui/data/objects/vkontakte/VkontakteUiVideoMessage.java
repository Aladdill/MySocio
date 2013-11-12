/**
 * 
 */
package net.mysocio.ui.data.objects.vkontakte;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class VkontakteUiVideoMessage extends VkontakteUiMessage {
	public static final String NAME = "VkontakteVideoMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public VkontakteUiVideoMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "vkVideoMessage.html";
	}
}
