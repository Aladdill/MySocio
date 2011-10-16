/**
 * 
 */
package net.mysocio.connection.writers;

import net.mysocio.data.ISocioObject;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public interface IDestination extends ISocioObject{
	public void postMessage(IMessage message);
}
