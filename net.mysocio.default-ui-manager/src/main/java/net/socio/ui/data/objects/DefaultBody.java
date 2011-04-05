/**
 * 
 */
package net.socio.ui.data.objects;


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
