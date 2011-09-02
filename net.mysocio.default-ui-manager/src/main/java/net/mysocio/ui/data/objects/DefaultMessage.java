/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.UiObject;
import net.mysocio.ui.managers.basic.DefaultResourcesManager;

/**
 * @author Aladdin
 *
 */
public class DefaultMessage extends UiObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790983136433676389L;
	private static final String NAME = "DefaultMessage";
	
	public DefaultMessage(){
		super();
		setName(NAME);
		addTextLabel("message.star");
		addTextLabel("message.like");
		addTextLabel("message.unlike");
		addTextLabel("message.edit.tags");
		addTextLabel("message.share");
		addTextLabel("message.keep.unread");
		addTextLabel("message.expand");
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("message.html");
	}
}
