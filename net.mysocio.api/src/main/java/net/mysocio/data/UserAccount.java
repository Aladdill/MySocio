/**
 * 
 */
package net.mysocio.data;

import net.mysocio.data.accounts.Account;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author DH67CL
 *
 */
@Entity("my_socio_user_accounts")
public class UserAccount extends UserObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5657336848804525987L;
	@Reference
	private Account account;

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
}
