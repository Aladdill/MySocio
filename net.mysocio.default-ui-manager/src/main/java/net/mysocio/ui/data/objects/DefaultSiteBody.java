/**
 * 
 */
package net.mysocio.ui.data.objects;


/**
 * @author Aladdin
 *
 */
public class DefaultSiteBody extends SiteBody{
	private static final String NAME = "DefaultSiteBody";
	
	public DefaultSiteBody(){
		super();
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		StringBuffer output = new StringBuffer();
		output.append("<table class='DefaultTable' cellpadding=\"0px\" cellspacing=\"0px\">");
		output.append("<colgroup>");
		output.append("<col width=\"20%\">");
		output.append("<col width=\"80%\">");
		output.append("</colgroup>");
		output.append("<tr>");
		output.append("<td>");
		output.append("<table class='DefaultTable' cellpadding=\"0px\" cellspacing=\"0p\">");
		output.append("<tr>");
		output.append("<td class='UserBox'>");
		output.append("<!-- User box -->"); 
		output.append("<div class='UserBox'>");
		output.append("<img class='Userpic' alt=\"Aladdin\" title=\"Aladdin\" src=\"images/portrait.jpg\" onclick=\"\">");
		output.append("<img class='Settings' alt=\"Settings\" title=\"Settings\" src=\"images/shesterenka25px.png\" onclick=\"openSettings()\">");
		output.append("<img class='Logout' alt=\"Logout\" title=\"Logout\" src=\"images/Power-Button-25px.png\" onclick=\"logout()\">");
		output.append("</div>");
		output.append("</td>");
		output.append("</tr>");
		output.append("<tr>");
		output.append("<!-- sources -->"); 
		output.append("<td class='SourcesBox'>");
		output.append("<div id=\"sources_tree\" class='SourcesBox'></div>");
//		output.append("<script>initSources()</script>");
		output.append("</td>");
		output.append("</tr>");
		output.append("</table>");
		output.append("</td>");
		output.append("<!-- messages -->");
		output.append("<td>");
		output.append("<div id='data_container' class ='MessagesBox'></div>");
//		output.append("<script>getMessages(\""+ GetMessagesExecutor.ALL_MESSAGES_PLACEHOLDER +"\")</script>");
		output.append("</td>");
		output.append("</tr>");
		output.append("</table>");
		return output.toString();
	}
}
