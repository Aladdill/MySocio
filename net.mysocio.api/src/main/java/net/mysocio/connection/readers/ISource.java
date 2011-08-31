/**
 * 
 */
package net.mysocio.connection.readers;

import net.mysocio.data.ISocioObject;

/**
 * @author Aladdin
 *
 */
public interface ISource extends ISocioObject{
	public String getName();

	public abstract String getUrl();

	public Class getMessageClass();
}
