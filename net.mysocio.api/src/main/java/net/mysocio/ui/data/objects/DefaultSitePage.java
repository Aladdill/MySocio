/**
 * 
 */
package net.mysocio.ui.data.objects;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class DefaultSitePage extends SitePage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3768494662798972463L;
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
	public String getPageFile() {
		return  "sitePage.html";
	}
}
