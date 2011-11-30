/**
 * 
 */
package net.mysocio.data.ui;

import java.util.Map;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Value;

import net.mysocio.data.SocioObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class UserUiObjects extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4563232180995547625L;
	private Long userId;
	@Join
	@Key(types=String.class)
    @Value(types=UiObject.class)
	private Map<String, IUiObject> userUiObjects;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Map<String, IUiObject> getUserUiObjects() {
		return userUiObjects;
	}
	public void setUserUiObjects(Map<String, IUiObject> userUiObjects) {
		this.userUiObjects = userUiObjects;
	} 
}
