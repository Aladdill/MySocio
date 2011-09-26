/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.ui.managers.basic.DefaultResourcesManager;


/**
 * @author Aladdin
 *
 */
public class DefaultSiteBody extends SiteBody{
	/**
	 * 
	 */
	private static final long serialVersionUID = -670751438184509172L;
	private static final String NAME = "DefaultSiteBody";
	
	public DefaultSiteBody(){
		super();
		setName(NAME);
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
	public String getHtmlTemplate() {
		return DefaultResourcesManager.getPage("mainPage.html");
	}
}
