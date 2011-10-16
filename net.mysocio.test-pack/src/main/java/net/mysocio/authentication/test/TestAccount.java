/**
 * 
 */
package net.mysocio.authentication.test;

import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 *
 */
public class TestAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8260188677369879519L;
	private static final String ACCOUNT_TYPE = "test";

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

}
