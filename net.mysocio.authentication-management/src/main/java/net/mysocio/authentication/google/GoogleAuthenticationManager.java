/**
 * 
 */
package net.mysocio.authentication.google;


import net.mysocio.authentication.AbstractOauth2Manager;
import net.mysocio.authentication.UserIdentifier;
import net.mysocio.data.Account;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class GoogleAuthenticationManager extends AbstractOauth2Manager{
	private static final Logger logger = LoggerFactory.getLogger(GoogleAuthenticationManager.class);
	
	@Override
	protected Logger getLogger(){
		return logger;
	}
	@Override
	protected UserIdentifier getUserIdentifier() {
		return UserIdentifier.google;
	}
	@Override
	protected Account getAccount(String tokenResponce) {
//		Account account = new googleacc
		try {
			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser(tokenResponce);
			jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)
			while (jp.nextToken() != JsonToken.END_OBJECT) {
			  System.out.println(jp.getCurrentName());
			  jp.nextToken(); // move to value, or START_OBJECT/START_ARRAY
			  System.out.println(jp.getText());
			}
		} catch (Exception e) {
			logger.error("Creating google account failed",e);
		}
		return null;
	}
}
