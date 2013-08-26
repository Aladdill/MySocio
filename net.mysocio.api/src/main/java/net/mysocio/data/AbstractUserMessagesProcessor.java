/**
 * 
 */
package net.mysocio.data;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity(value="my_socio_user_messages_processors")
public abstract class AbstractUserMessagesProcessor extends UserObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7931242946498803813L;
	public abstract void process()  throws Exception;
}
