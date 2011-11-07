/**
 * 
 */
package net.mysocio.data.management;

import org.apache.camel.Body;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Aladdin
 *
 */
public interface IRssMessagesRidingBean {
	public void readMessages(@Body SyndFeed feed);
	public String getId();

	public void setId(String id);

	public String getUrl();

	public void setUrl(String url);
}
