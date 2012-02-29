/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class NamedFacebookObject extends NamedObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3395293894155152292L;
	private String facebookId;

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
}
