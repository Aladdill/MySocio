/**
 * 
 */
package net.mysocio.data.messages.rss;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.SourceAwareMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class RssMessage extends SourceAwareMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8628206019126311936L;
}
