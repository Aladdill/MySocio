/**
 * 
 */
package net.mysocio.data;

import java.util.Collections;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class SocioObject implements ISocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1428112468244972968L;
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX, primaryKey="true")
	protected String id;
	@NotPersistent
	private SocioObjectTags tags = new SocioObjectTags();
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Set<SocioTag> getTags() {
		if (tags == null){
			return Collections.emptySet();
		}
		return tags.getTags();
	}

	public void setTags(SocioObjectTags tags) {
		this.tags = tags;
	}

	public void addTag(SocioTag tag) {
		this.tags.add(tag);
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
