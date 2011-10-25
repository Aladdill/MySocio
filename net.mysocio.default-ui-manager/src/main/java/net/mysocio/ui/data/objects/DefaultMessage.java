/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.ui.UiObject;

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
		addTextLabel("message.collapse");
	}
	@Override
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("message.html");
	}
}
