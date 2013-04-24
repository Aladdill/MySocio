/**
 * 
 */
package net.mysocio.ui.data.objects;

import com.github.jmkgreen.morphia.annotations.Entity;


/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class DefaultBody extends HtmlBody {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4663411632799856862L;
	private static final String NAME = "DefaultHtmlBody";
	private SitePage sitePage = new DefaultSitePage();
	
	public DefaultBody(){
		setName(NAME);
		addInnerObject(sitePage, 1);
	}
	
	@Override
	protected String getInnerHtml() {
		return sitePage.getObjectTag(1);
	}
}
