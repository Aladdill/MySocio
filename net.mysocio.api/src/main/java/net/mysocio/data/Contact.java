/**
 * 
 */
package net.mysocio.data;

import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.SourcesGroup;
import net.mysocio.connection.writers.DestinationsGroup;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Contact extends NamedObject implements IContact {
	@Persistent
	private SourcesGroup sourcesGroup = new SourcesGroup();
	@Persistent
	private DestinationsGroup destinationsGroup = new DestinationsGroup();
	@Persistent(nullValue=NullValue.DEFAULT)
	private String userpicUrl;
	@Persistent(nullValue=NullValue.DEFAULT)
	private String email;
	

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setUserpicUrl(String userpicUrl) {
		this.userpicUrl = userpicUrl;
	}

	/**
	 * @return the group
	 */
	public SourcesGroup getSourcesGroup() {
		return sourcesGroup;
	}

	/**
	 * @param group the group to set
	 */
	public void setSourcesGroup(SourcesGroup group) {
		this.sourcesGroup = group;
	}

	@Override
	public String getUserpicUrl() {
		return userpicUrl;
	}

	@Override
	public void addSource(ISource source) {
		this.sourcesGroup.addSource(source);
	}

	@Override
	public void addSources(List<? extends ISource> sources) {
		this.sourcesGroup.addSources(sources);		
	}

	@Override
	public List<ISource> getSources() {
		return this.sourcesGroup.getSources();
	}

	/**
	 * @return the destinationsGroup
	 */
	public DestinationsGroup getDestinationsGroup() {
		return destinationsGroup;
	}

	/**
	 * @param destinationsGroup the destinationsGroup to set
	 */
	public void setDestinationsGroup(DestinationsGroup destinationsGroup) {
		this.destinationsGroup = destinationsGroup;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.NamedObject#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		sourcesGroup.setName(name);
		destinationsGroup.setName(name);
		super.setName(name);
	}
}
