/**
 * 
 */
package net.mysocio.ui.data.objects.rss;

import net.mysocio.ui.data.objects.DefaultMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class RssUiMessage extends DefaultMessage {
	public static final String NAME = "RssMessage";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793278518382672999L;

	public RssUiMessage() {
		super();
		setName(NAME);
		addTextLabel("rss.message.from");
	}
	
	@Override
	public String getPageFile() {
		return "rssMessage.html";
	}
}
