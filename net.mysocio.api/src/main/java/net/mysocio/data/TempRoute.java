/**
 * 
 */
package net.mysocio.data;

import com.google.code.morphia.annotations.CappedAt;
import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity(value="temp_routes",cap=@CappedAt(100000))
public class TempRoute extends SocioRoute {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2259795808674773636L;
	
	private long creationDate;

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
}
