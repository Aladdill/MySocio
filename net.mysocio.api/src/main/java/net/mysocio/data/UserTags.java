/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.List;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("my_socio_user_tags")
public class UserTags extends SocioObject {
	public static final String ALL_TAGS = "all.tags";
	private String userId;
	private String value;
	private boolean showAll = false;
	private String order = SocioTag.ASCENDING_ORDER;
	
	@com.github.jmkgreen.morphia.annotations.Embedded
	private List<SocioTag> children = new ArrayList<SocioTag>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 2321183060150148692L;

	public UserTags(){
		setValue(ALL_TAGS);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public List<SocioTag> getLeaves(){
		List<SocioTag> leaves = new ArrayList<SocioTag>();
		for (SocioTag child : children) {
			leaves.addAll(child.getLeaves());
		}
		return leaves;
	}
	
	public SocioTag createTag(String uniqueId, String value, SocioTag parent){
		return createTag(uniqueId, value, null, parent);
	}
	
	public SocioTag createTag(String uniqueId, String value, String iconType, SocioTag parent){
		for (SocioTag child : children) {
			SocioTag desendant = child.getDesendant(uniqueId);
			if (desendant != null){
				return desendant;
			}
		}
		SocioTag child = new SocioTag(uniqueId, value, iconType);
		parent.addChild(child);
		return child;
	}
	
	public SocioTag getTag(String uniqueId){
		SocioTag desendant = null;
		for (SocioTag child : children) {
			desendant = child.getDesendant(uniqueId);
			if (desendant != null){
				return desendant;
			}
		}
		return desendant;
	}
	
	public void removeTag(String uniqueId){
		for (SocioTag child : children) {
			child.removeDesendant(uniqueId);
		}
	}
	
	public SocioTag createTag(String uniqueId, String value, String iconType){
		for (SocioTag child : children) {
			SocioTag desendant = child.getDesendant(uniqueId);
			if (desendant != null){
				return desendant;
			}
		}
		SocioTag child = new SocioTag(uniqueId, value, iconType);
		addChild(child);
		return child;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTags other = (UserTags) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
