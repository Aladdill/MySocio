/**
 * 
 */
package net.mysocio.data;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Id;



/**
 * @author Aladdin
 *
 */
public abstract class SocioObject implements ISocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1428112468244972968L;
	@Id
	protected ObjectId id;
	
	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SocioObject other = (SocioObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
