/**
 * 
 */
package net.mysocio.authentication.google;

import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuth20ServiceImpl;

/**
 * @author Aladdin
 *
 */
public class GoogleOAuth2Service extends OAuth20ServiceImpl {
	private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE = "grant_type";
    private GoogleOauth2Api api;
    private OAuthConfig config;

    public GoogleOAuth2Service(GoogleOauth2Api api, OAuthConfig config) {
        super(api, config);
        this.api = api;
        this.config = config;
    }
    
    @Override
    public Token getAccessToken(Token requestToken, Verifier verifier) {
        OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        switch (api.getAccessTokenVerb()) {
        case POST:
            request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
            request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
            request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
            request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
            request.addBodyParameter(GRANT_TYPE, GRANT_TYPE_AUTHORIZATION_CODE);
            break;
        case GET:
        default:
            request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
            request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
            request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
            request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
            if(config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        }
        Response response = request.send();
        return api.getAccessTokenExtractor().extract(response.getBody());
    }
}

