/**
 * 
 */
package net.mysocio.data;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public class StringWrapper extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2714150419789780582L;
	private String string;

	public StringWrapper() {
		super();
	}
	
	public StringWrapper(String string) {
		super();
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
