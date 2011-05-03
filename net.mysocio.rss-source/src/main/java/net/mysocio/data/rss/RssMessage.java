/**
 * 
 */
package net.mysocio.data.rss;

import javax.persistence.Entity;

import net.mysocio.data.GeneralMessage;

/**
 * @author gurfinke
 *
 */
@Entity
public class RssMessage extends GeneralMessage {
	public RssMessage() {
		super();
	}
	public RssMessage(String link) {
		super();
		setLink(link);
	}
}
