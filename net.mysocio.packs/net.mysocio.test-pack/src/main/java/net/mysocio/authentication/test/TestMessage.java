/**
 * 
 */
package net.mysocio.authentication.test;

import net.mysocio.data.messages.GeneralMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("messages")
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
