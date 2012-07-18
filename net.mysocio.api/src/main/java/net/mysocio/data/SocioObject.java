/**
 * 
 */
package net.mysocio.data;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;



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
		if (id == null){
			return "".hashCode();
		}
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getId().equals(((SocioObject)obj).getId());
	}
}
