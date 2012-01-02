/**
 * 
 */
package net.mysocio.data.messages.facebook;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.UserMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
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
}
