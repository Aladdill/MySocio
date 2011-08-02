/**
 * 
 */
package net.mysocio.authentication.facebook;

import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.authentication.UserIdentifier;
import net.mysocio.data.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class FacebookAuthenticationManager extends AbstractOauth2Manager{
	private static final Logger logger = LoggerFactory.getLogger(FacebookAuthenticationManager.class);
	
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected UserIdentifier getUserIdentifier() {
		return UserIdentifier.facebook;
	}

	@Override
	protected Account getAccount(String tokenResponce) {
		return null;
	}
}
