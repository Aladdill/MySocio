/**
 * 
 */
package net.mysocio.data;

import javax.persistence.MappedSuperclass;

/**
 * @author Aladdin
 *
 */
@MappedSuperclass
public class NamedObject extends SocioObject implements INamedObject{

	protected String name;

	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
