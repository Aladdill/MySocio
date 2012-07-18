/**
 * 
 */
package net.mysocio.data.messages.rss;

import net.mysocio.data.messages.GeneralMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("messages")
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
