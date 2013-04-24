/**
 * 
 */
package net.mysocio.data.accounts.lj;

import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.Destination;
import net.mysocio.data.contacts.Contact;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("friends")
public class LjFriend extends Contact{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999857199400431354L;

	@Override
	public String getUniqueFieldName() {
		return "url";
	}

	@Override
	public Object getUniqueFieldValue() {
		return getUrl();
	}

	@Override
	public List<Source> getSources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Destination> getDestinations() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
