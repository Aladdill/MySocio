/**
 * 
 */
package net.mysocio.connection.readers;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
public abstract class AccountSource extends Source {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4346691940766962601L;
	private Account account; 

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
