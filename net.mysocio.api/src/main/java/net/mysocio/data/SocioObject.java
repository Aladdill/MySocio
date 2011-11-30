/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class SocioObject implements ISocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1428112468244972968L;
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX, primaryKey="true")
	protected String id;
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getId().equals(((SocioObject)obj).getId());
	}
}
