/**
 * 
 */
package net.mysocio.data.messages;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.ui.UiObject;
import net.mysocio.ui.data.objects.DefaultMessage;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
public abstract class GeneralMessage extends SocioObject implements IMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = 352828420023827718L;
	private String uniqueId =  new String();
	private String title =  new String();
	private String text =  new String();
	private Set<SocioTag> tags = new HashSet<SocioTag>();
	
	private long date;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the link
	 */
	public String getUniqueId() {
		return uniqueId;
	}
	/**
	 * @param link the link to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String replacePlaceholders(String template) {
		String message = template.replace("message.title", getTitle());
		message = message.replace("message.id", getId());
		message = message.replace("message.text", getText());
		message = message.replace("message.link", getLink());
		return message;
	}

	public Set<SocioTag> getTags() {
		return tags;
	}
	public void addTag(SocioTag tag){
		tags.add(tag);
	}
	public abstract String getLink();

	@Override
	public String getUiCategory() {
		return DefaultMessage.CATEGORY;
	}

	@Override
	public String getUiName() {
		return DefaultMessage.NAME;
	}
}
