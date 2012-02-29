/**
 * 
 */
package net.mysocio.authentication.test;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.GeneralMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class TestMessage extends GeneralMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343088110385165921L;

	@Override
	public String getLink() {
		return getUniqueId();
	}

}
