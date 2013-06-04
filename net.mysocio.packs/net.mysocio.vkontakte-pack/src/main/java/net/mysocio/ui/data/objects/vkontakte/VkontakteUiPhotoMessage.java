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
public class VkontakteUiPhotoMessage extends VkontakteUiMessage {
	public static final String NAME = "FacebookPhotoMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public VkontakteUiPhotoMessage() {
		super();
		setName(NAME);
	}
	
	@Override
	public String getPageFile() {
		return "fbPhotoMessage.html";
	}
}
