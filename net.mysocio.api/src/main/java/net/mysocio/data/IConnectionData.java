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

	public abstract SocioUser getUser();

	public abstract void cleanSession();

	public abstract void setUser(SocioUser user);

	public abstract Locale getLocale();

	public void removeSessionAttribute(String attributeName);
}
