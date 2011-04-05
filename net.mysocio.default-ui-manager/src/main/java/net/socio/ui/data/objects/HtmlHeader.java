/**
 * 
 */
package net.socio.ui.data.objects;

import net.mysocio.data.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class HtmlHeader extends UiObject {
	private static final String CATEGORY = "HtmlHeader";
	
	public HtmlHeader(){
		setCategory(CATEGORY);
	}
	
	protected abstract String getInnerHtml();
	
	@Override
	public String getHtmlTemplate() {
		return ("<head>" + getInnerHtml() + "</head>");
	}
}
