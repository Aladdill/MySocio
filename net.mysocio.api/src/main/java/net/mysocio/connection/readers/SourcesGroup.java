/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@Entity
public class SourcesGroup extends NamedObject implements ISourcesGroup{
	@ManyToMany(targetEntity = Source.class, fetch = FetchType.EAGER)
	@JoinTable(name="sources_topology",joinColumns = {@JoinColumn(name = "parent_id")}, inverseJoinColumns = {@JoinColumn(name = "child_id")})
	private List<ISource> sources = new ArrayList<ISource>();

	@Override
	public List<ISource> getSources() {
		return sources;
	}

	@Override
	public void addSources(List<? extends ISource> sources) {
		for (ISource source : sources) {
			addSource(source);
		}
	}
	@Override
	public void addSource(ISource source) {
		this.sources.add(source);
	}
}
