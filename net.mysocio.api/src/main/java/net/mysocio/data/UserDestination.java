/**
 * 
 */
package net.mysocio.data;

import net.mysocio.connection.writers.Destination;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author DH67CL
 *
 */
@Entity("my_socio_user_destinations")
public class UserDestination extends UserObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3031464015028016564L;
	@Reference
	private Destination destination;
	/**
	 * @return the destination
	 */
	public Destination getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
}
