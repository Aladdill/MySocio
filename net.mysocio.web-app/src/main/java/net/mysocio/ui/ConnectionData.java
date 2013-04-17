/**
 * 
 */
package net.mysocio.ui;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.UserTags;

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
	private UserTags userTags;
	private String selectedTag;
	
	public ConnectionData(HttpServletRequest request){
		this.request = request;
		session = request.getSession();
		locale = request.getLocale();
		if (logger.isDebugEnabled()){
			logger.debug("Request received from country " + locale.getCountry() + " and language " + locale.getLanguage());
		}
	}
	@Override
	public String getCookieValue(String name){
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)){
				return cookie.getValue();
			}
		}
		return null;
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
	public UserTags getUserTags() {
		return userTags;
	}
	public void setUserTags(UserTags userTags) {
		this.userTags = userTags;
	}
	public String getSelectedTag() {
		return selectedTag;
	}
	public void setSelectedTag(String selectedTag) {
		this.selectedTag = selectedTag;
	}
}
