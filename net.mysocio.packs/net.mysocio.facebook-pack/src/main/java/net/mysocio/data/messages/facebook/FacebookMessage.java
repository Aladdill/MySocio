/**
 * 
 */
package net.mysocio.data.messages.facebook;

import net.mysocio.data.messages.UserMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("messages")
public class FacebookMessage extends UserMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5110172305702339723L;

	@Override
	public String getNetworkIcon() {
		return "images/networksIcons/fb.png";
	}

	@Override
	public String getReadenNetworkIcon() {
		return "images/networksIcons/fb-gray.png";
	}

	@Override
	public String getLink() {
		return "https://facebook.com/" + getUserId();
	}

	@Override
	public String getUiCategory() {
		return FacebookUiMessage.CATEGORY;
	}

	@Override
	public String getUiName() {
		return FacebookUiMessage.NAME;
	}
}
