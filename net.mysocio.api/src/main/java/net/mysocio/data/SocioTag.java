/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SocioTag extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7408921930602565552L;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
