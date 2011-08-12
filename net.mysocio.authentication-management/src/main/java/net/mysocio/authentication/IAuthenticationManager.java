/**
 * 
 */
package net.mysocio.authentication;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 *
 */
public interface IAuthenticationManager {
	public String getRequestUrl();
	public Account login(IConnectionData connectionData) throws Exception;
}
