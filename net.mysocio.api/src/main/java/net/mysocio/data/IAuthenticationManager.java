/**
 * 
 */
package net.mysocio.data;

import net.mysocio.data.accounts.Account;


/**
 * @author Aladdin
 *
 */
public interface IAuthenticationManager {
	public String getRequestUrl();
	public Account getAccount(IConnectionData connectionData) throws Exception;
}
