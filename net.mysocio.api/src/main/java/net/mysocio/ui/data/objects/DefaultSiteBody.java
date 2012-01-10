/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;


/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")


public class DefaultSiteBody extends SiteBody{
	/**
	 * 
	 */
	private static final long serialVersionUID = -670751438184509172L;
	public static final String NAME = "DefaultSiteBody";
	public static final String CATEGORY = "SiteBody";
	
	public DefaultSiteBody(){
		super();
		setName(NAME);
		setCategory(CATEGORY);
		addTextLabel("link.settings");
		addTextLabel("link.back.to.main.page");
		addTextLabel("link.logout");
		addTextLabel("tab.accounts");
		addTextLabel("tab.contacts");
		addTextLabel("tab.rss.feeds");
		addTextLabel("tab.rss.feeds");
		addTextLabel("subscriptions.title");
	}
	@Override
	public String getPageFile() {
		return "mainPage.html";
	}
}
