/**
 * 
 */
package net.mysocio.data.messages.rss;

import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.ui.data.objects.rss.RssUiMessage;

import com.github.jmkgreen.morphia.annotations.Entity;

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
	
	/**
	 * 
	 */
	private String link;
	private String feedTitle;

	@Override
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public Object getUniqueFieldValue() {
		return link;
	}

	@Override
	public String getUniqueFieldName() {
		return "link";
	}

	@Override
	public String getUiName() {
		return RssUiMessage.NAME;
	}
	public String replacePlaceholders(String template) {
		String message = super.replacePlaceholders(template);
		message = message.replace("message.link", getLink());
		if (getFeedTitle() != null){
			message = message.replace("feed.title", getFeedTitle());
		}else{
			message = message.replace("feed.title", "Unknown");
		}
		return message;
	}

	public String getFeedTitle() {
		return feedTitle;
	}

	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
}
