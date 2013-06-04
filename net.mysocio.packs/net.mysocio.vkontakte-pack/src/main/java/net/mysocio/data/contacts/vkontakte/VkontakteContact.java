/**
 * 
 */
package net.mysocio.data.contacts.vkontakte;

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
public class VkontakteContact extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405708860373380037L;
	
	private String vkontakteId;

	@Override
	public List<Source> getSources() {
		return Collections.emptyList();
	}

	@Override
	public List<Destination> getDestinations() {
		return Collections.emptyList();
	}


	public String getIcon() {
		return "images/networksIcons/v-kontakte.png";
	}

	@Override
	public String getUrl() {
		return "http://vk.com/id" + getVkontakteId();
	}

	@Override
	public String getUniqueFieldName() {
		return "vkontakteId";
	}

	@Override
	public Object getUniqueFieldValue() {
		return getVkontakteId();
	}

	public String getVkontakteId() {
		return vkontakteId;
	}

	public void setVkontakteId(String vkontakteId) {
		this.vkontakteId = vkontakteId;
	}
}
