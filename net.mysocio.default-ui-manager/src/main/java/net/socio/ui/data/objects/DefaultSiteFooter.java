/**
 * 
 */
package net.socio.ui.data.objects;

/**
 * @author Aladdin
 *
 */
public class DefaultSiteFooter extends SiteFooter {
	private static final String NAME = "DefaultSiteFooter";
	public DefaultSiteFooter(){
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		return "footer";
	}
}
