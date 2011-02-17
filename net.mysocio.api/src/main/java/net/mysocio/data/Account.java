/**
 * 
 */
package net.mysocio.data;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Aladdin
 *
 */
@Entity(name="accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account extends NamedObject {
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
