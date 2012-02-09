/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class DefaultMessage extends UiObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790983136433676389L;
	public static final String NAME = "DefaultMessage";
	public static final String CATEGORY = "Message";
	
	public DefaultMessage(){
		super();
		setName(NAME);
		setCategory(CATEGORY);
		addTextLabel("message.star");
		addTextLabel("message.like");
		addTextLabel("message.unlike");
		addTextLabel("message.edit.tags");
		addTextLabel("message.share");
		addTextLabel("message.keep.unread");
		addTextLabel("message.expand");
		addTextLabel("message.collapse");
	}
	@Override
	public String getPageFile() {
		return "message.html";
	}
}
