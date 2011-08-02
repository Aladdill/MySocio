/**
 * 
 */
package net.mysocio.data;

import net.mysocio.connection.writers.IDestination;

/**
 * @author Aladdin
 *
 */
public interface IContact extends IDestination, INamedObject {
	public String getUserpicUrl();

	public abstract void setUserpicUrl(String userpicUrl);
}
