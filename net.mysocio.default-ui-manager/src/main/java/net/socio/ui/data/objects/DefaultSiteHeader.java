/**
 * 
 */
package net.socio.ui.data.objects;


/**
 * @author Aladdin
 *
 */
public class DefaultSiteHeader extends SiteHeader {
	private static final String NAME = "DefaultSiteHeader";
	public DefaultSiteHeader(){
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		return "<img class='HeaderImage' alt=\"Hungry Octopus Devouring the Net\" title=\"Hungry Octopus Devouring the Net\" src=\"images/socio-logo-small.png\">";
	}
}
