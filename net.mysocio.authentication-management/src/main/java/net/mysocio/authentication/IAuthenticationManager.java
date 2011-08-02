/**
 * 
 */
package net.mysocio.authentication;

import net.mysocio.data.Account;
import net.mysocio.data.IConnectionData;

/**
 * @author Aladdin
 *
 */
public interface IAuthenticationManager {
	public String getRequestUrl();
	public Account login(IConnectionData connectionData) throws Exception;
}
