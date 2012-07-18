/**
 * 
 */
package net.mysocio.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.mysocio.data.IConnectionData;

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
	private String userId;
	
	public ConnectionData(HttpServletRequest request){
		this.request = request;
		session = request.getSession();
		locale = request.getLocale();
		if (logger.isDebugEnabled()){
			logger.debug("Request received from country " + locale.getCountry() + " and language " + locale.getLanguage());
		}
	}
	public String getUserId(){
		return userId;
	}
	public void cleanSession() {
		session.removeAttribute("user");
		userId = null;
	}
	public String getRequestParameter(String parameterName){
		return request.getParameter(parameterName);
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Locale getLocale() {
		return locale;
	}
	public String getSessionAttribute(String attributeName) {
		return (String)session.getAttribute(attributeName);
	}
	public void removeSessionAttribute(String attributeName) {
		session.removeAttribute(attributeName);
	}
	public void setSessionAttribute(String attributeName, String attributeValue) {
		session.setAttribute(attributeName, attributeValue);
	}
}
