/**
 * 
 */
package net.mysocio.data;

import javax.persistence.Entity;



/**
 * @author Aladdin
 *
 */
@Entity(name="contacts")
public class SocioContact extends Contact implements IContact {

	public void setName(String name) {
	}

	public void setUserpicUrl(String userpicUrl) {
	}

	public void setEmail(String email) {
	}

	public String getEmail() {
		return null;
	}
}
