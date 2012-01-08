/**
 * 
 */
package net.mysocio.data.ui;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
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
	private String userId;
	private Map<String, IUiObject> userUiObjects = new HashMap<String, IUiObject>();
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, IUiObject> getUserUiObjects() {
		return userUiObjects;
	}
	public void setUserUiObjects(Map<String, IUiObject> userUiObjects) {
		this.userUiObjects = userUiObjects;
	}
	public void addUserUiObject(IUiObject object){
		this.userUiObjects.put(object.getCategory(), object);
	}
}
