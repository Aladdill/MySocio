/**
 * 
 */
package net.socio.ui.data.basic;

import net.mysocio.data.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class HtmlPage extends UiObject {
	private static final String CATEGORY = "HtmlPage";
	private HtmlBody body;
	private HtmlHeader header;
	public HtmlPage(HtmlBody body, HtmlHeader header){
		setCategory(CATEGORY);
		addInnerObject(header, 1);
		addInnerObject(body, 1);
	}
	@Override
	public String getHtmlTemplate() {
		return "<html>" + header.getObjectTag(1) + body.getObjectTag(1) + "</html>";
	}
}
