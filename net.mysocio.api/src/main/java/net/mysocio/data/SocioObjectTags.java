/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SocioObjectTags implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7193595035950826005L;
	@PrimaryKey
	private String id;
	@Persistent
	private Set<SocioTag> tags = new HashSet<SocioTag>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void add(SocioTag tag) {
		tags.add(tag);
	}

	public Set<SocioTag> getTags() {
		return tags;
	}

	public void setTags(Set<SocioTag> tags) {
		this.tags = tags;
	}
}
