/**
 * 
 */
package net.mysocio.data.messages;

import net.mysocio.data.IUniqueObject;
import net.mysocio.data.SocioObject;
import net.mysocio.ui.data.objects.DefaultMessage;

import com.google.code.morphia.annotations.Entity;



/**
 * @author Aladdin
 *
 */
@Entity("messages")
public abstract class GeneralMessage extends SocioObject implements IUniqueObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 352828420023827718L;
	private String uniqueId =  new String();
	private String title =  new String();
	private String text =  new String();
	
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

	public abstract String getLink();

	public String getUiCategory() {
		return DefaultMessage.CATEGORY;
	}

	public String getUiName() {
		return DefaultMessage.NAME;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUniqueObject#getUniqueFieldName()
	 */
	@Override
	public String getUniqueFieldName() {
		return "uniqueId";
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUniqueObject#getUniqueFieldValue()
	 */
	@Override
	public Object getUniqueFieldValue() {
		return getUniqueId();
	}
	
	public String replacePlaceholders(String template) {
		String message = template.replace("message.title", getTitle());
		message = message.replace("message.id", getUniqueId());
		message = message.replace("message.text", getText());
		message = message.replace("message.link", getLink());
		return message;
	}
}
