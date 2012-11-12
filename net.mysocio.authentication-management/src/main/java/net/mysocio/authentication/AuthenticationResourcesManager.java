/**
 * 
 */
package net.mysocio.authentication;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class AuthenticationResourcesManager {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationResourcesManager.class);
	private static ResourceBundle authentication;
	public static void init(String contextPath){
		if (authentication == null){
			try {
				authentication = ResourceBundle.getBundle("authentication");
			} catch (Exception e) {
				logger.error("No authentication properties were found.",e);
			}
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
	public static String getResource(String resource){
		return authentication.getString(resource);
	}
}
