/**
 * 
 */
package net.mysocio.data.ui;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import net.mysocio.data.SocioObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class UserUiObjects extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4563232180995547625L;
	private String userId;
	private Map<String, UiObject> userUiObjects = new HashMap<String, UiObject>();
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, UiObject> getUserUiObjects() {
		return userUiObjects;
	}
	public void setUserUiObjects(Map<String, UiObject> userUiObjects) {
		this.userUiObjects = userUiObjects;
	}
	public void addUserUiObject(UiObject object){
		this.userUiObjects.put(object.getCategory(), object);
	}
}
