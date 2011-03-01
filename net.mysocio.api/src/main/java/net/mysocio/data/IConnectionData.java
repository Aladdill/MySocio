/**
 * 
 */
package net.mysocio.data;

/**
 * @author Aladdin
 *
 */
public interface IConnectionData {

	public abstract String getRequestParameter(String parameterName);

	public abstract SocioUser getUser();

	public abstract void cleanSession();

	public abstract void setUser(SocioUser user);

}
