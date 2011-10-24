/**
 * 
 */
package net.mysocio.connection.readers;

import net.mysocio.data.ITagedObject;

/**
 * @author Aladdin
 *
 */
public interface ISource extends ITagedObject{
	public String getName();

	public abstract String getUrl();

	public ISourceManager getManager();
}
