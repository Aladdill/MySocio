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

	public String getUniqueId();

	public void setSourceId(String sourceId);

	public String getSourceId();

	public void setUniqueId(String uniqueId);

	public void setText(String text);

	public void setTitle(String title);
	
	public String replacePlaceholders(String template);

	public String getTitle();
	
	public long getDate();
	
	public void setDate(long date);
}
