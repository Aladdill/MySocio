/**
 * 
 */
package net.socio.ui.data.basic;

import net.mysocio.data.UiObject;

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
