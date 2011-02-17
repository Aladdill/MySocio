/**
 * 
 */
package net.mysocio.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;



/**
 * @author Aladdin
 *
 */
@Entity(name = "messages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GeneralMessage extends SocioObject implements IMessage{
	private String link;
	private String title;
	private String text;
	private Long sourceId;

	public GeneralMessage() {
		super();
	}

	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	@Override
	public void setText(String text) {
		String cutText = cutMessageText(text);
		this.text = cutText;
	}
	
	/**
	 * @return the link
	 */
	@Override
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	@Override
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the source
	 */
	public Long getSourceId() {
		return sourceId;
	}

	/**
	 * @param source the source to set
	 */
	public void setSourceId(Long sourceId) {
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
