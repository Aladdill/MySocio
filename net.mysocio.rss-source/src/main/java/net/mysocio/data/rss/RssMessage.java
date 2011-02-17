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
	Long publishedDate;
	public RssMessage() {
		super();
	}
	public RssMessage(String link) {
		super();
		setLink(link);
	}
	/**
	 * @return the publishedDate
	 */
	public Long getPublishedDate() {
		return publishedDate;
	}
	/**
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(Long publishedDate) {
		this.publishedDate = publishedDate;
	}
	
}
