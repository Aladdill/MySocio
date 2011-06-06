/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public class SocioObject implements ISocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1428112468244972968L;
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX, primaryKey="true")
	protected String id;
	@Join
	@Persistent(types={SocioTag.class},mappedBy = "value")
	private List<SocioTag> tags = new ArrayList<SocioTag>();
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public List<SocioTag> getTags() {
		return tags;
	}

	public void setTags(List<SocioTag> tags) {
		this.tags = tags;
	}

	public void addTag(SocioTag tag) {
		this.tags.add(tag);
	}
}
