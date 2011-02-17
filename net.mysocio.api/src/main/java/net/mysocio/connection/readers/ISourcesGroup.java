/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.List;

import net.mysocio.data.INamedObject;

/**
 * @author gurfinke
 *
 */
public interface ISourcesGroup extends INamedObject {

	public abstract void addSource(ISource reader);

	public abstract void addSources(List<? extends ISource> sources);

	public abstract List<ISource> getSources();
}
