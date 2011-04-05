/**
 * 
 */
package net.socio.ui.data.objects;

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
		StringBuffer output = new StringBuffer();
		output.append("<table class='DefaultTable'>");
		output.append("<!-- header -->");
		output.append("<tr class='PageHeader'>");
		output.append("<td>");
		output.append(siteHeader.getObjectTag(1));
		output.append("</td>");
		output.append("</tr>");
		output.append("<!-- body -->");
		output.append("<tr class='PageBody'>");
		output.append("<td>");
		output.append("<div id=\"SiteBody\" class=\"fullSize\">");
		output.append(siteBody.getObjectTag(1));
		output.append("</div>");
		output.append("</td>");
		output.append("</tr>");
		output.append("<!-- footer -->");
		output.append("<tr class='PageFooter'>");
		output.append("<td>");
		output.append(siteFooter.getObjectTag(1));
		output.append("</td>");
		output.append("</tr>");
		output.append("</table>");
		return  output.toString();
	}
}
