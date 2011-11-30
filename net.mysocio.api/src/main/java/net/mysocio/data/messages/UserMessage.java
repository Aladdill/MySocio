/**
 * 
 */
package net.mysocio.data.messages;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
public abstract class UserMessage extends GeneralMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3132822032864353865L;
	private String userPic;
	private String userId;

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	
	
	
	public abstract String getNetworkIcon();
	public abstract String getReadenNetworkIcon();

	@Override
	public String replacePlaceholders(String template) {
		String message = super.replacePlaceholders(template);
		message = message.replace("network.icon.readen", getReadenNetworkIcon());
		message = message.replace("network.icon", getNetworkIcon());
		return message;
	}

	@Override
	public String getText() {
		return "<div class=\"MessageUserpic\"><img alt=\"UserPic\" src=\""+ getUserPic() +"\" class=\"MessageUserpic\"></div>" + super.getText();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
