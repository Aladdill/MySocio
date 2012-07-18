/**
 * 
 */
package net.mysocio.data.contacts;

import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.Destination;
import net.mysocio.data.NamedObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("contacts")
public abstract class Contact extends NamedObject{
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

	public abstract List<Source> getSources();
	
	public abstract List<Destination> getDestinations();

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
