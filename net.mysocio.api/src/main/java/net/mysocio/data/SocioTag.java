/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class SocioTag extends SocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7408921930602565552L;
	
	private String value;
	private String iconType;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}
}
