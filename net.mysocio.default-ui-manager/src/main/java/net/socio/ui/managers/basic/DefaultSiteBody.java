/**
 * 
 */
package net.socio.ui.managers.basic;

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
		output.append("<!-- body -->");
		output.append("<tr class='PageBody'>");
		output.append("<td>");
		output.append("<table>");// style='width : 100%; height : 100%;'>" +
//				"<colgroup>" +
//				"<col width=\"20%\">" +
//				"<col width=\"80%\">" +
//				"</colgroup>");
		//style='width : 100%; height : 100%;'
		output.append("<tr>");
		output.append("<!-- sources -->"); 
		output.append("<td>");
		output.append("<div id=\"sources_tree\" class='SourcesBox'></div>");
		output.append("<script>initSources()</script>");
		output.append("</td>");
		output.append("<!-- messages -->");
		output.append("<td>");
		output.append("<div id='data_container' class ='MessagesBox'></div>");
		output.append("<script>getMessages(all)</script>");
		output.append("</td>");
		output.append("</tr>");
		output.append("</table>");
		output.append("</td>");
		output.append("</tr>");
		return output.toString();
	}
}
