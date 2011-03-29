/**
 * 
 */
package net.socio.ui.managers.basic;


/**
 * @author Aladdin
 *
 */
public class DefaultSiteHeader extends SiteHeader {
	@Override
	public String getHtmlTemplate() {
		StringBuffer output = new StringBuffer();
		output.append("<!-- header -->");
		output.append("<tr class='PageHeader'>");
		output.append("<td>");
		output.append("<img class='HeaderImage' alt=\"Hungry Octopus Devouring the Net\" src=\"images/socio-logo-small.png\">");
		output.append("</td>");
		output.append("</tr>");
		return output.toString();
	}
}
