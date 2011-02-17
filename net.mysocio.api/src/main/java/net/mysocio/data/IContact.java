/**
 * 
 */
package net.mysocio.data;

import net.mysocio.connection.readers.ISourcesGroup;
import net.mysocio.connection.writers.IDestinatioin;

/**
 * @author Aladdin
 *
 */
public interface IContact extends ISourcesGroup, IDestinatioin {
	public String getUserpicUrl();

	public abstract void setName(String name);

	public abstract void setUserpicUrl(String userpicUrl);

	public abstract void setEmail(String email);

	public abstract String getEmail();
}
