/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.AbstractProcessor;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("processors")
public abstract class UserRouteProcessor extends AbstractProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5999483128638397312L;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
