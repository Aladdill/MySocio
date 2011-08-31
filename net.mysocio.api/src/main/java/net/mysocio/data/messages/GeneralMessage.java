/**
 * 
 */
package net.mysocio.data.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private String link =  new String();
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
		String cutText = cutMessageText(text);
		this.text = cutText;
	}
	
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
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
	
	/**
	 * Cleans text from HTML tags and cuts it to 250 symbols
	 * @param text
	 * @return
	 */
	private static String cutMessageText(String text) {
		Pattern p = Pattern.compile("<(.|\n)+?>");
		Matcher matcher = p.matcher(text);
		String cutText = matcher.replaceAll("");
		int textLength = Math.min(cutText.length(), 250);
		cutText = cutText.substring(0,textLength) + " ...";
		return cutText;
	}
}
