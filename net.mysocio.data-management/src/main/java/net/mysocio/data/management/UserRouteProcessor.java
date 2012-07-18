/**
 * 
 */
package net.mysocio.data.management;

import org.apache.camel.Processor;

/**
 * @author Aladdin
 *
 */
public abstract class UserRouteProcessor implements Processor {
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
