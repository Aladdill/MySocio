/**
 * 
 */
package net.mysocio.data;


/**
 * @author Aladdin
 *
 */
public interface IAuthenticationManager {
	public String authenticate(IConnectionData connectionData) throws Exception;
}
