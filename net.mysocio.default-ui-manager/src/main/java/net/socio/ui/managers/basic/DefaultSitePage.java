/**
 * 
 */
package net.socio.ui.managers.basic;

/**
 * @author Aladdin
 *
 */
public class DefaultSitePage extends SitePage {
	private static final String NAME = "DefaultSitePage";
	private SiteHeader siteHeader = new DefaultSiteHeader();
	private SiteBody siteBody = new DefaultSiteBody();
	private SiteFooter siteFooter = new DefaultSiteFooter();
	
	public DefaultSitePage(){
		super();
		setName(NAME);
		addInnerObject(siteHeader, 1);
		addInnerObject(siteBody, 1);
		addInnerObject(siteFooter, 1);
	}

	@Override
	public String getHtmlTemplate() {
		return "<table class='SitePage'>" + siteHeader.getObjectTag(1) + siteBody.getObjectTag(1) + siteFooter.getObjectTag(1) + "</table>";
	}
}
