/**
 * 
 */
package net.mysocio.data;

import net.mysocio.connection.readers.Source;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author DH67CL
 *
 */
@Entity("my_socio_user_sources")
public class UserSource extends UserObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6192052171103160688L;
	@Reference
	private Source source;
	private String order = SocioUser.ASCENDING_ORDER;

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}
}
