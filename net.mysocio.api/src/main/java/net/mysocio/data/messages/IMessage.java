/**
 * 
 */
package net.mysocio.data.messages;

import net.mysocio.data.ISocioObject;



/**
 * @author Aladdin
 *
 */
public interface IMessage extends ISocioObject {

	public String getText();

	public abstract String getLink();

	public abstract void setSourceId(String sourceId);

	public abstract String getSourceId();

	public abstract void setLink(String link);

	public abstract void setText(String text);

	public abstract void setTitle(String title);

	public abstract String getTitle();
	
	public abstract Long getDate();
	
	public void setDate(Long date);
}
