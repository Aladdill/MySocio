/**
 * 
 */
package net.mysocio.data.messages.rss;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.GeneralMessage;

/**
 * @author gurfinke
 *
 */
@PersistenceCapable
public class RssMessage extends GeneralMessage {
	private String sourceId;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8628206019126311936L;
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
