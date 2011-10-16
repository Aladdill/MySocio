/**
 * 
 */
package net.mysocio.data.contacts;

import net.mysocio.data.INamedObject;

/**
 * @author Aladdin
 *
 */
public interface IContact extends INamedObject {
	public String getUserpicUrl();

	public abstract void setUserpicUrl(String userpicUrl);
}
