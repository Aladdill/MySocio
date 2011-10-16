/**
 * 
 */
package net.mysocio.authentication.google;

import static org.scribe.utils.URLUtils.formURLEncode;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;

/**
 * @author Aladdin
 *
 */
public class GoogleOauth2Api extends DefaultApi20 {

	private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/auth?client_id=%s&scope=%s&redirect_uri=%s&response_type=code";
	@Override
	public String getAccessTokenEndpoint() {
		return "https://accounts.google.com/o/oauth2/token";
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config) {
		return String.format(AUTHORIZE_URL, config.getApiKey(),config.getScope(), formURLEncode(config.getCallback()));
	}
}
