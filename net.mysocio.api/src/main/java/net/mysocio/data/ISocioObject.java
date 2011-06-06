/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;
import java.util.List;



/**
 * @author Aladdin
 *
 */
public interface ISocioObject extends Serializable{
	public String getId();

	public List<SocioTag> getTags();
}
