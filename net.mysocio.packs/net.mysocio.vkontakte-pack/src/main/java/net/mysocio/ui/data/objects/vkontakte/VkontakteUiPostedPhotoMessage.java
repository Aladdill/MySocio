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
public class VkontakteUiPostedPhotoMessage extends VkontakteUiMessage {
	public static final String NAME = "FacebookCheckinMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public VkontakteUiPostedPhotoMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbCheckinMessage.html";
	}
}
