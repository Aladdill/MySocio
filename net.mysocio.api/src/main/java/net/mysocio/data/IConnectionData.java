/**
 * 
 */
package net.mysocio.data;

import java.util.Locale;

/**
 * @author Aladdin
 *
 */
public interface IConnectionData {

	public abstract String getRequestParameter(String parameterName);
	
	public abstract String getSessionAttribute(String attributeName);
	
	public abstract void setSessionAttribute(String attributeName, String attributeValue);

	public abstract String getUserId();

	public abstract void cleanSession();

	public abstract void setUserId(String user);

	public abstract Locale getLocale();

	public void removeSessionAttribute(String attributeName);
}
