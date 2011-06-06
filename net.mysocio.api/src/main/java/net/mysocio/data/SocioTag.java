/**
 * 
 */
package net.mysocio.data;

import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SocioTag implements ISocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7408921930602565552L;
	@Persistent
	@PrimaryKey
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return value;
	}

	public List<SocioTag> getTags() {
		throw new UnsupportedOperationException();
	}
}
