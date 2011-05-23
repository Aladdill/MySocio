/**
 * 
 */
package net.mysocio.data.rss;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.GeneralMessage;

/**
 * @author gurfinke
 *
 */
@PersistenceCapable
public class RssMessage extends GeneralMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8628206019126311936L;
	public RssMessage() {
		super();
	}
	public RssMessage(String link) {
		super();
		setLink(link);
	}
}
