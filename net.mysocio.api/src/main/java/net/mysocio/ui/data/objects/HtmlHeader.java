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
public abstract class HtmlHeader extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2857520668603948153L;
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
