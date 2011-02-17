/**
 * 
 */
package net.mysocio.connection.readers.lj;

import javax.persistence.Entity;

import net.mysocio.data.Account;

/**
 * @author Aladdin
 *
 */
@Entity
public class LjUser extends Account {
	public LjUser(String userName, String password) {
		super();
		setName(userName);
		setUserName(userName);
		setPassword(password);
	}
}
