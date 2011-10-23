/**
 * 
 */
package net.mysocio.data.messages;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.data.SocioObject;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class GeneralMessage extends SocioObject implements IMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = 352828420023827718L;
	@Persistent
	private String uniqueId =  new String();
	@Persistent
	private String title =  new String();
	@Persistent
	private String text =  new String();
	@Persistent
	private String sourceId =  new String();
	
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

	/**
	 * @return the source
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param source the source to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String replacePlaceholders(String template) {
		String message = template.replace("message.title", getTitle());
		message = message.replace("message.id", getSourceId() + "_" + getId());
		message = message.replace("message.text", getText());
		message = message.replace("message.link", getUniqueId());
		return message;
	}
	
	
}
