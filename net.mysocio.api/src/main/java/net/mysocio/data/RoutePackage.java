/**
 * 
 */
package net.mysocio.data;

import com.google.code.morphia.annotations.CappedAt;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity(value="route_packages", cap=@CappedAt(10000000))
public class RoutePackage extends SocioObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7528594299467902046L;
	private String to;
	@Embedded
	private SocioObject object;
	private Long creationDate;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public SocioObject getObject() {
		return object;
	}
	public void setObject(SocioObject object) {
		this.object = object;
	}
	public Long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}
}
