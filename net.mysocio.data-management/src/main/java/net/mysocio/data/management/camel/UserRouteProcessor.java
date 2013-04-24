/**
 * 
 */
package net.mysocio.data.management.camel;

import net.mysocio.data.AbstractProcessor;

/**
 * @author Aladdin
 *
 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRouteProcessor other = (UserRouteProcessor) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
