/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.Persistent;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**
 * @author gurfinke
 *
 */
@MappedSuperclass
public class SocioObject implements ISocioObject{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Persistent(primaryKey="true")
	protected Long id;
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}
}
