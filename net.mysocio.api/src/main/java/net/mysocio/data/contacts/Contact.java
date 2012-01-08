/**
 * 
 */
package net.mysocio.data.contacts;

import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
public abstract class Contact extends NamedObject implements IContact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8721056150291879634L;
	private String userpicUrl = "images/portrait.jpg";
	private String url;
	private String socioContactId;
	
	public void setUserpicUrl(String userpicUrl) {
		this.userpicUrl = userpicUrl;
	}

	public String getUserpicUrl() {
		return userpicUrl;
	}

	public abstract List<ISource> getSources();
	
	public abstract List<IDestination> getDestinations();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSocioContactId() {
		return socioContactId;
	}

	public void setSocioContactId(String socioContactId) {
		this.socioContactId = socioContactId;
	}
}
