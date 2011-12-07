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
	public static String getAuthenticationId(String identifier){
		return authentication.getString(identifier + ".id");
	}
	public static String getAuthenticationSecret(String identifier){
		return authentication.getString(identifier + ".secret");
	}
	public static String getAuthenticationRequestUrl(String identifier){
		return authentication.getString(identifier + ".request.url");
	}
	public static String getAuthenticationTokenUrl(String identifier){
		return authentication.getString(identifier + ".token.url");
	}
	public static String getAuthenticationScope(String identifier){
		return authentication.getString(identifier + ".scope");
	}
	public static String getRedirectUrl(){
		return authentication.getString("redirect.url");
	}
}
