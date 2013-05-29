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
		addTextLabel("post.here");
		addTextLabel("post.button");
		addTextLabel("mark.all.read.button");
		addTextLabel("order.by.oldest.button");
		addTextLabel("order.by.newest.button");
		addTextLabel("import.opml");
		addTextLabel("enter.rss.url");
		addTextLabel("choose.file");
		addTextLabel("search.for.tag");
		addTextLabel("add.facebook.account");
		addTextLabel("add.google.account");
		addTextLabel("add.twitter.account");
		addTextLabel("add.vkontakte.account");
	}
	@Override
	public String getPageFile() {
		return "mainPage.html";
	}
}
