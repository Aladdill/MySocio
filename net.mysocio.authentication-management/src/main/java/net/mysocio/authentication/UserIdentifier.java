/**
 * 
 */
package net.mysocio.authentication;

import net.mysocio.authentication.facebook.FacebookAuthenticationManager;
import net.mysocio.authentication.google.GoogleAuthenticationManager;
import net.mysocio.authentication.linkedin.LinkedinAuthenticationManager;
import net.mysocio.authentication.twitter.TwitterAuthenticationManager;
import net.mysocio.authentication.vkontakte.VkontakteAuthenticationManager;

/**
 * @author Aladdin
 *
 *
 */
public enum UserIdentifier {
	google(new GoogleAuthenticationManager()),
	facebook(new FacebookAuthenticationManager()),
	twiter(new TwitterAuthenticationManager()),
	vkontakte(new VkontakteAuthenticationManager()),
	linkedin(new LinkedinAuthenticationManager());
	private IAuthenticationManager authManager;

	private UserIdentifier(IAuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	public IAuthenticationManager getAuthManager() {
		return authManager;
	}
}
