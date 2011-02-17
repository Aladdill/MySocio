/**
 * 
 */
package net.mysocio.connection.writers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


/**
 * @author gurfinke
 *
 */
@Entity
public class DestinationsGroup extends Destination {
	@ManyToMany(targetEntity = Destination.class)
	@JoinTable(name="destination_topology",joinColumns = {@JoinColumn(name = "parent_id")}, inverseJoinColumns = {@JoinColumn(name = "child_id")})
	private List<IDestinatioin> destinations = new ArrayList<IDestinatioin>();
	
	public List<IDestinatioin> getDestinations() {
		return destinations;
	}
	
	public void addDestinations(List<? extends IDestinatioin> destinations) {
		for (IDestinatioin destination : destinations) {
			addDestination(destination);
		}
	}
	public void addDestination(IDestinatioin destination) {
		this.destinations.add(destination);
	}
}
