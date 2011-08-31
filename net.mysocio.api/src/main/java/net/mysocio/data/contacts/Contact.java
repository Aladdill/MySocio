/**
 * 
 */
package net.mysocio.data.contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Contact extends NamedObject implements IContact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8721056150291879634L;
	@Join
	@Persistent
	private Set<ISource> sources = new HashSet<ISource>();
	@Join
	@Persistent
	private Set<IDestination> destinations = new HashSet<IDestination>();
	@Persistent
	private String userpicUrl =  new String();
	
	public void setUserpicUrl(String userpicUrl) {
		this.userpicUrl = userpicUrl;
	}

	public String getUserpicUrl() {
		return userpicUrl;
	}

	public void addSource(ISource source) {
		this.sources.add(source);
	}

	public void addSources(List<? extends ISource> sources) {
		this.sources.addAll(sources);		
	}

	public Set<ISource> getSources() {
		return this.sources;
	}
	
	public Set<IDestination> getDestinations() {
		return destinations;
	}
	
	public void addDestination(IDestination destination) {
		this.destinations.add(destination);
	}

	public void addDestinations(List<? extends IDestination> destinations) {
		this.destinations.addAll(destinations);		
	}
}