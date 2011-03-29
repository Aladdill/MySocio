/**
 * 
 */
package net.socio.ui.managers.basic;

import net.socio.ui.data.basic.HtmlBody;

/**
 * @author Aladdin
 *
 */
public class DefaultBody extends HtmlBody {
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
