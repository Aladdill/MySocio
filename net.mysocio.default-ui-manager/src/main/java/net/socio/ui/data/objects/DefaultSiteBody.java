/**
 * 
 */
package net.socio.ui.data.objects;

import net.socio.ui.executors.basic.GetMessagesExecutor;

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
		output.append("<table class='DefaultTable'>");
		output.append("<colgroup>");
		output.append("<col width=\"20%\">");
		output.append("<col width=\"80%\">");
		output.append("</colgroup>");
		output.append("<tr>");
		output.append("<!-- sources -->"); 
		output.append("<td>");
		output.append("<div  class='UserBox'>");
		output.append("<img class='Userpic' alt=\"Aladdin\" title=\"Aladdin\" src=\"images/portrait.jpg\" onclick=\"\">");
		output.append("<img class='Settings' alt=\"Settings\" title=\"Settings\" src=\"images/shesterenka25px.png\" onclick=\"openSettings()\">");
		output.append("<img class='Logout' alt=\"Logout\" title=\"Logout\" src=\"images/Power-Button-25px.png\" onclick=\"logout()\">");
		output.append("</div>");
		output.append("<div id=\"sources_tree\" class='SourcesBox'></div>");
		output.append("<script>initSources()</script>");
		output.append("</td>");
		output.append("<!-- messages -->");
		output.append("<td>");
		output.append("<div id='data_container' class ='MessagesBox'></div>");
		output.append("<script>getMessages(\""+ GetMessagesExecutor.ALL_MESSAGES_PLACEHOLDER +"\")</script>");
		output.append("</td>");
		output.append("</tr>");
		output.append("</table>");
		return output.toString();
	}
}
