/**
 * 
 */
package net.mysocio.data.management;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class ConnectionData implements IConnectionData{
	private static final Logger logger = LoggerFactory.getLogger(ConnectionData.class);
	private HttpSession session;
	private HttpServletRequest request;
	private Locale locale;
	
	public ConnectionData(HttpServletRequest request){
		this.request = request;
		session = request.getSession();
		locale = request.getLocale();
		if (logger.isDebugEnabled()){
			logger.debug("Request received from country " + locale.getCountry() + " and language " + locale.getLanguage());
		}
	}
	public SocioUser getUser(){
		return (SocioUser)session.getAttribute("user");
	}
	public void cleanSession() {
		session.removeAttribute("user");
	}
	public String getRequestParameter(String parameterName){
		return request.getParameter(parameterName);
	}
	public void setUser(SocioUser user) {
		session.setAttribute("user", user);
		if (logger.isDebugEnabled()){
			logger.debug("User was inserted into session with name " + user.getName() + " and email " + user.getEmail());
		}
	}
	
	public Locale getLocale() {
		return locale;
	}
}
