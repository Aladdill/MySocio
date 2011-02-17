/**
 * 
 */
package net.mysocio.data;



/**
 * @author Aladdin
 *
 */
public interface IMessage extends ISocioObject {

	public String getText();

	public abstract String getLink();

	public abstract void setSourceId(Long sourceId);

	public abstract Long getSourceId();

	public abstract void setLink(String link);

	public abstract void setText(String text);

	public abstract void setTitle(String title);

	public abstract String getTitle(); 
}
