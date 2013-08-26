/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


/**
 * @author Aladdin
 *
 */
@Entity
public class SocioTag implements Serializable, Comparable<SocioTag>{
	public static final String ASCENDING_ORDER = "ascending";
	public static final String DESCENDING_ORDER = "descending";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7408921930602565552L;
	private String value;
	private String iconType;
	@Id
	private String uniqueId;
	@Embedded
	private List<SocioTag> children = new ArrayList<SocioTag>();
	private String order = SocioTag.ASCENDING_ORDER;
	
	public SocioTag(){}

	/**
	 * @param value
	 * @param iconType
	 */
	public SocioTag(String uniqueId, String value) {
		super();
		this.uniqueId = uniqueId;
		this.value = value;
	}
	
	public SocioTag(String uniqueId, String value, String iconType) {
		super();
		this.uniqueId = uniqueId;
		this.value = value;
		this.iconType = iconType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}
	
	public List<SocioTag> getLeaves(){
		List<SocioTag> leaves = new ArrayList<SocioTag>();
		if (children.isEmpty()){
			leaves.add(this);
		}else{
			for (SocioTag child : children) {
				leaves.addAll(child.getLeaves());
			}
		}
		return leaves;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocioTag other = (SocioTag) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public List<SocioTag> getChildren() {
		return children;
	}

	public void setChildren(List<SocioTag> children) {
		this.children = children;
	}
	public void addChild(SocioTag child) {
		this.children.add(child);
	}
	public void removeChild(SocioTag child) {
		this.children.remove(child);
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public SocioTag getDesendant(String uniqueId) {
		SocioTag desendant = null;
		if (this.uniqueId.equals(uniqueId)){
			return this;
		}
		for (SocioTag child : children) {
			desendant = child.getDesendant(uniqueId);
			if (desendant != null){
				return desendant;
			}
		}
		return desendant;
	}

	public void removeDesendant(String uniqueId) {
		SocioTag childFound = null;
		for (SocioTag child : children) {
			if (child.getUniqueId().equals(uniqueId)){
				childFound = child;
			}else{
				child.removeDesendant(uniqueId);
			}
		}
		if (childFound != null){
			children.remove(childFound);
		}
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public int compareTo(SocioTag o) {
		return value.compareTo(o.value);
	}
}
