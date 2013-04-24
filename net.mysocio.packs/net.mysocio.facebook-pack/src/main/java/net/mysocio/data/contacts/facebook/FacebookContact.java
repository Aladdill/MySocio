/**
 * 
 */
package net.mysocio.data.contacts.facebook;

import java.util.Collections;
import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.Destination;
import net.mysocio.data.contacts.Contact;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("contacts")
public class FacebookContact extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405708860373380037L;
	
	private String facebookId;

	@Override
	public List<Source> getSources() {
		return Collections.emptyList();
	}

	@Override
	public List<Destination> getDestinations() {
		return Collections.emptyList();
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getIcon() {
		return "images/FacebookSource-icon.jpg";
	}

	@Override
	public String getUserpicUrl() {
		return "https://graph.facebook.com/" + getFacebookId() + "/picture";
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.contacts.Contact#getUrl()
	 */
	@Override
	public String getUrl() {
		return "https://www.facebook.com/" + getFacebookId();
	}

	@Override
	public String getUniqueFieldName() {
		return "facebookId";
	}

	@Override
	public Object getUniqueFieldValue() {
		return getFacebookId();
	}
}
