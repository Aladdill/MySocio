/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public abstract class HtmlPage extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1091785907896685234L;
	private static final String CATEGORY = "HtmlPage";
	private HtmlBody body;
	private HtmlHeader header;
	public HtmlPage(HtmlBody body, HtmlHeader header){
		setCategory(CATEGORY);
		this.body = body;
		this.header = header;
		addInnerObject(header, 1);
		addInnerObject(body, 1);
	}
	@Override
	public String getHtmlTemplate() {
		return "<html>" + header.getObjectTag(1) + body.getObjectTag(1) + "</html>";
	}
}
