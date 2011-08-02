/**
 * 
 */
package net.mysocio.authentication;

import java.util.ResourceBundle;

/**
 * @author Aladdin
 *
 */
public class AuthenticationResourcesManager {
	private static ResourceBundle authentication;
	public static void init(String contextPath){
		if (authentication == null){
			authentication = ResourceBundle.getBundle("authentication");
		}
	}
	public static String getAuthenticationId(UserIdentifier identifier){
		return authentication.getString(identifier.name() + ".id");
	}
	public static String getAuthenticationSecret(UserIdentifier identifier){
		return authentication.getString(identifier.name() + ".secret");
	}
	public static String getAuthenticationRequestUrl(UserIdentifier identifier){
		return authentication.getString(identifier.name() + ".request.url");
	}
	public static String getAuthenticationTokenUrl(UserIdentifier identifier){
		return authentication.getString(identifier.name() + ".token.url");
	}
	public static String getAuthenticationScope(UserIdentifier identifier){
		return authentication.getString(identifier.name() + ".scope");
	}
}
