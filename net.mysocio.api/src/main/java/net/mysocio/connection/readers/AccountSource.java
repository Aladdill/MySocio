/**
 * 
 */
package net.mysocio.connection.readers;

import net.mysocio.data.accounts.Account;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public abstract class AccountSource extends Source {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4346691940766962601L;
	@Reference
	private Account account; 

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
