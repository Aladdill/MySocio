/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class HtmlBody extends UiObject {
	private static final String CATEGORY = "HtmlBody";
	
	public HtmlBody(){
		setCategory(CATEGORY);
	}
	
	protected abstract String getInnerHtml();
	
	@Override
	public String getHtmlTemplate() {
		return ("<body>" + getInnerHtml() + "</body>");
	}
}
