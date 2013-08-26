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
public class SocioPair extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2714150419789780582L;
	private String value1;
	private String value2;

	public SocioPair() {
		super();
	}

	/**
	 * @param value1
	 * @param value2
	 */
	public SocioPair(String value1, String value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
}
