/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SourcesGroup extends NamedObject implements ISourcesGroup{
	@Join()
	@Persistent(types={Source.class},mappedBy = "id")
	private List<ISource> sources = new ArrayList<ISource>();

	public List<ISource> getSources() {
		return sources;
	}

	public void addSources(List<? extends ISource> sources) {
		for (ISource source : sources) {
			addSource(source);
		}
	}
	public void addSource(ISource source) {
		this.sources.add(source);
	}
}
