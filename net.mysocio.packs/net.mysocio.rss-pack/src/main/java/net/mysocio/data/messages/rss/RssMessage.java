/**
 * 
 */
package net.mysocio.data.messages.rss;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.GeneralMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class RssMessage extends GeneralMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8628206019126311936L;

	@Override
	public String getLink() {
		return getUniqueId();
	}
}
