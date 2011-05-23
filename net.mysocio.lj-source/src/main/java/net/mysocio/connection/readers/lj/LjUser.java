/**
 * 
 */
package net.mysocio.connection.readers.lj;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class LjUser extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3439808496783622338L;

	public LjUser(String userName, String password) {
		super();
		setName(userName);
		setUserName(userName);
		setPassword(password);
	}
}
