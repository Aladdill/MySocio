/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Account extends NamedObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7561557374532619473L;
	@Persistent(nullValue=NullValue.DEFAULT)
	private String userName;
	@Persistent(nullValue=NullValue.DEFAULT)
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
