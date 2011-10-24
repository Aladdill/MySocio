/**
 * 
 */
package net.mysocio.ui.data.objects;

/**
 * @author Aladdin
 *
 */
public class DefaultSiteFooter extends SiteFooter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1604571537611447189L;
	private static final String NAME = "DefaultSiteFooter";
	public DefaultSiteFooter(){
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		return "footer";
	}
}
