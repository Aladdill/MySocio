/**
 * 
 */
package net.mysocio.data.contacts;

import net.mysocio.data.INamedObject;
import net.mysocio.data.ITagedObject;

/**
 * @author Aladdin
 *
 */
public interface IContact extends INamedObject, ITagedObject {
	public String getUserpicUrl();

	public abstract void setUserpicUrl(String userpicUrl);
}
