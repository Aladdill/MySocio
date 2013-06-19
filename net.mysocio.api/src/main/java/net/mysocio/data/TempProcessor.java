/**
 * 
 */
package net.mysocio.data;

import com.github.jmkgreen.morphia.annotations.CappedAt;
import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity(value="temp_user_processors",cap=@CappedAt(100000))
public class TempProcessor extends AbstractUserProcessor {
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
