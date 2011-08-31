/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;
import java.util.Set;



/**
 * @author Aladdin
 *
 */
public interface ISocioObject extends Serializable{
	public String getId();

	public Set<SocioTag> getTags();
	
	public void setTags(SocioObjectTags tags);
}
