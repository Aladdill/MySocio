/**
 * 
 */
package net.mysocio.data.messages.facebook;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.UserMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookMessage extends UserMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5110172305702339723L;

	@Override
	public String getNetworkIcon() {
		return "images/main/message/fb.png";
	}

	@Override
	public String getReadenNetworkIcon() {
		return "images/main/message/fb-gray.png";
	}
}
