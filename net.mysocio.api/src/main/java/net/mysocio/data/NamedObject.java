/**
 * 
 */
package net.mysocio.data;



/**
 * @author Aladdin
 *
 */
public abstract class NamedObject extends SocioObject implements INamedObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4752863295913329595L;
	protected String name =  new String();

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
