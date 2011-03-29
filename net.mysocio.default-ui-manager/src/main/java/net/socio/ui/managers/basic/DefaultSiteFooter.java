/**
 * 
 */
package net.socio.ui.managers.basic;

/**
 * @author Aladdin
 *
 */
public class DefaultSiteFooter extends SiteFooter {
	@Override
	public String getHtmlTemplate() {
		StringBuffer output = new StringBuffer(); 
		output.append("<!-- footer -->");
		output.append("<tr class='PageFooter'>");
		output.append("<td>");
		output.append("footer");
		output.append("</td>");
		output.append("</tr>");
		return output.toString();
	}
}
