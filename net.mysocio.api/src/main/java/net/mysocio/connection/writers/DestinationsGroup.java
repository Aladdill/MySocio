/**
 * 
 */
package net.mysocio.connection.writers;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;


/**
 * @author gurfinke
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class DestinationsGroup extends Destination {
	@Join
	@Persistent(types={Destination.class},mappedBy = "id")
	private List<IDestination> destinations = new ArrayList<IDestination>();
	
	public List<IDestination> getDestinations() {
		return destinations;
	}
	
	public void addDestinations(List<? extends IDestination> destinations) {
		for (IDestination destination : destinations) {
			addDestination(destination);
		}
	}
	public void addDestination(IDestination destination) {
		this.destinations.add(destination);
	}
}
