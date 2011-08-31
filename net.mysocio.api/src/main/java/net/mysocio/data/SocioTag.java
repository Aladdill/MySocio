/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SocioTag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7408921930602565552L;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX, primaryKey="true")
	protected String id;
	@Persistent
	private String value;
	@Persistent
	private String ownerId;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}
}
