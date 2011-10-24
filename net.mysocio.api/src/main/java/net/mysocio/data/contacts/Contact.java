/**
 * 
 */
package net.mysocio.data.contacts;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.NamedObject;
import net.mysocio.data.SocioTag;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public abstract class Contact extends NamedObject implements IContact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8721056150291879634L;
	private List<ISource> sources = new ArrayList<ISource>();
	private List<IDestination> destinations = new ArrayList<IDestination>();
	private String userpicUrl =  new String();
	private List<SocioTag> tags = new ArrayList<SocioTag>();
	
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

	public List<ISource> getSources() {
		return this.sources;
	}
	
	public List<IDestination> getDestinations() {
		return destinations;
	}
	
	public void addDestination(IDestination destination) {
		this.destinations.add(destination);
	}

	public void addDestinations(List<? extends IDestination> destinations) {
		this.destinations.addAll(destinations);		
	}

	public List<SocioTag> getTags() {
		return tags;
	}

	public void setTags(List<SocioTag> tags) {
		this.tags = tags;
	}
}
