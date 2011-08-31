/**
 * 
 */
package net.mysocio.data.contacts;

import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.INamedObject;

/**
 * @author Aladdin
 *
 */
public interface IContact extends IDestination, INamedObject {
	public String getUserpicUrl();

	public abstract void setUserpicUrl(String userpicUrl);
}
