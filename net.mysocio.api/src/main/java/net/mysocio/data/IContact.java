/**
 * 
 */
package net.mysocio.data;

import net.mysocio.connection.writers.IDestination;

/**
 * @author Aladdin
 *
 */
public interface IContact extends IDestination {
	public String getUserpicUrl();

	public abstract void setName(String name);

	public abstract void setUserpicUrl(String userpicUrl);

	public abstract void setEmail(String email);

	public abstract String getEmail();
}
