/**
 * 
 */
package net.mysocio.data.messages;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.ui.data.objects.UserUiMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
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
		message = message.replace("user.pic", getUserPic());
		return message;
	}
	
	public String getPageFile() {
		return "userMessage.html";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUiCategory() {
		return UserUiMessage.CATEGORY;
	}

	@Override
	public String getUiName() {
		return UserUiMessage.NAME;
	}
}
