/**
 * 
 */
package net.mysocio.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import net.mysocio.data.Account;
import net.mysocio.data.IConnectionData;

import org.slf4j.Logger;


/**
 * @author Aladdin
 *
 */
public abstract class AbstractOauth2Manager implements IAuthenticationManager {

	protected String getBasicRequestUrl(){
		return AuthenticationResourcesManager.getAuthenticationRequestUrl(getUserIdentifier());
	}
	
	protected abstract UserIdentifier getUserIdentifier();

	protected String getMysocioId(){
		return AuthenticationResourcesManager.getAuthenticationId(getUserIdentifier());
	}

	protected String getMysocioRedirect(){
		return "http://mysocio.sytes.net/mysocio/login";
	}

	protected String getScope(){
		return AuthenticationResourcesManager.getAuthenticationScope(getUserIdentifier());
	}

	@Override
	public String getRequestUrl() {
		return getBasicRequestUrl() +
		"client_id=" + getMysocioId()+ "&" +
		"redirect_uri=" + getMysocioRedirect() + "&" +
		"scope=" + getScope() + "&" +
		"response_type=code";
	}

	protected String getMySocioSecret(){
		return AuthenticationResourcesManager.getAuthenticationSecret(getUserIdentifier());
	}

	protected String getTokenUrl(){
		return AuthenticationResourcesManager.getAuthenticationTokenUrl(getUserIdentifier());
	}

	protected abstract Account getAccount(String tokenResponce);

	protected abstract Logger getLogger();

	@Override
	public Account login(IConnectionData connectionData) throws Exception {
		URL url = new URL(getTokenUrl());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", 
		"application/x-www-form-urlencoded");
	
		String code = connectionData.getRequestParameter("code");
		String urlParameters = "code=" + URLEncoder.encode(code , "UTF-8") +
	    "&client_id=" + URLEncoder.encode(getMysocioId(), "UTF-8") +
	    "&redirect_uri=" + URLEncoder.encode(getMysocioRedirect(), "UTF-8") + 
	    "&client_secret=" + URLEncoder.encode(getMySocioSecret(), "UTF-8") + 
	    "&grant_type=" + URLEncoder.encode("authorization_code", "UTF-8");
		connection.setRequestProperty("Content-Length", "" + 
				Integer.toString(urlParameters.getBytes().length));
		connection.setRequestProperty("Content-Language", "en-US");  
	
		connection.setUseCaches (false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
	
		//Send request
		DataOutputStream wr = new DataOutputStream (
				connection.getOutputStream ());
		wr.writeBytes (urlParameters);
		wr.flush ();
		wr.close ();
	
		//Get Response	
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer(); 
		while((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		String tokenResponce = response.toString();
		getLogger().debug(tokenResponce);
		if(connection != null) {
			connection.disconnect(); 
		}
		return getAccount(tokenResponce);
	}

}
