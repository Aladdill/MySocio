/**
 * 
 */
package net.mysocio.data.contacts.facebook;

import java.util.Collections;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.writers.IDestination;
import net.mysocio.data.SocioTag;
import net.mysocio.data.contacts.Contact;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
public class FacebookContact extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405708860373380037L;
	
	private String facebookId;

	private List<SocioTag> tags;
	
	public List<SocioTag> getDefaultTags() {
		return Collections.emptyList();
	}

	public List<SocioTag> getTags() {
		return tags;
	}

	@Override
	public List<ISource> getSources() {
		return Collections.emptyList();
	}

	@Override
	public List<IDestination> getDestinations() {
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
	
}
