/**
 * 
 */
package net.socio.ui.managers.basic;

import net.mysocio.ui.management.IUiManager;


/**
 * @author Aladdin
 *
 */
public class DefaultUiManager implements IUiManager {
	private static final String DEFAULT_PATH = "login";
	public static final String SOURCE_ID = "sourceId";
	private static final String LIB_DHTMLX_DATA_VIEW_CODE_BASE = "lib/dhtmlxDataView/codebase/";
	private static final String LIB_DHTMLX_TREE_CODE_BASE = "lib/dhtmlxTree/codebase/";

	@Override
	public String getStartingPage(){
		StringBuffer output  = new StringBuffer("<html>");
		output.append("<head>");
		addCssImports(output);
		addJavascriptImports(output);
		output.append("</head>");
		output.append("<body>");
		output.append("<table style='height : 100%; width : 100%;'>");
		addPageHeader(output);
		addPageBody(output);
//		addPageFooter(output);
		output.append("</table>");
		output.append("</body>");
		output.append("</html>");
		return output.toString();
	}

	private void addJavascriptImports(StringBuffer output) {
		output.append("<script src=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "dhtmlxcommon.js\"></script>");
		output.append("<script src=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "dhtmlxtree.js\"></script>");
		output.append("<script src=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "ext/dhtmlxtree_start.js\"></script>");
		output.append("<script src=\""+LIB_DHTMLX_DATA_VIEW_CODE_BASE+"dhtmlxdataview.js\" type=\"text/javascript\"></script>");
		output.append("<script src=\"http://yui.yahooapis.com/3.2.0/build/yui/yui-min.js\"></script>");
		output.append("<script src=\"lib/utilities.js\"></script>");
	}

	private void addCssImports(StringBuffer output) {
		output.append("<link rel=\"STYLESHEET\" type=\"text/css\" href=\""+LIB_DHTMLX_DATA_VIEW_CODE_BASE+"dhtmlxdataview.css\">");
		output.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "dhtmlxtree.css\">");
	}

	/**
	 * @param output
	 */
	private void addPageBody(StringBuffer output) {
		output.append("<!-- body -->");
		output.append("<tr style='height : 80%;'>");
		output.append("<td>");
		output.append("<table style='width : 100%; height : 100%;'>" +
				"<colgroup>" +
				"<col width=\"20%\">" +
				"<col width=\"80%\">" +
				"</colgroup>");
		output.append("<tr style='width : 100%; height : 100%;'>");
		output.append("<!-- sources -->"); 
		output.append("<td>");
		output.append("<div id=\"sources_tree\" style=\" border:1px solid #A4BED4; background-color:white; width:100%; height:100%; overflow:auto;\"></div>");
		appendSourcesScript(output);
		output.append("</td>");
		output.append("<!-- messages -->");
		output.append("<td>");
		output.append("<div id='data_container' style='border:1px solid #A4BED4; background-color:white;width:100%; height:100%; overflow: auto;'></div>");
		appendMessagesScript(output);
		output.append("</td>");
		output.append("</tr>");
		output.append("</table>");
		output.append("</td>");
		output.append("</tr>");
	}

	/**
	 * @param output
	 */
	private void appendSourcesScript(StringBuffer output) {
		output.append("<script>" +
				"var tree = new dhtmlXTreeObject(\"sources_tree\",\"100%\",\"100%\",0);" +
				"tree.setImagePath(\"lib/dhtmlxTree/codebase/imgs/\");" +
				"tree.loadXML(\"" + DEFAULT_PATH + "?command=" + EDefaultCommand.getSources.name() + "\", function(){});" +
				"tree.attachEvent(\"onSelect\", function(id){" + getMessagesCommand(false) + "});" +
				"</script>");
	}

	/**
	 * @param output
	 */
	private void appendMessagesScript(StringBuffer output) {
		String getMessagesCommand = getMessagesCommand(true);
		output.append("<script>" + getMessagesCommand + "</script>");
	}

	/**
	 * @return
	 */
	private String getMessagesCommand(boolean all) {
		String getMessagesUrl = "var uri = \"" + DEFAULT_PATH + "?command=" + EDefaultCommand.getMessages.name();
		if (!all){
			getMessagesUrl += "&"+ SOURCE_ID +"=" +"\"+id;";
		}else{
			getMessagesUrl += "\";";
		}
		String getMessagesCommand = "YUI().use(\"io-base\", \"node\", function(Y) {" + 
							getMessagesUrl +
							"function complete(id, o, args) {" +
								"var data = o.responseText;" +
								"var node = Y.one('#data_container');" +
								"node.set('innerHTML', data);" +
							"};" +
						"Y.on('io:complete', complete, Y, []);" +
						"var request = Y.io(uri);" +
						"});";
		return getMessagesCommand;
	}
	
	/**
	 * @param output
	 */
	private void addPageFooter(StringBuffer output) {
		output.append("<!-- footer -->");
		output.append("<tr style='height : 10%;'>");
		output.append("<td style='border : solid 1px; width : 100%;'>");
		output.append("footer");
		output.append("</td>");
		output.append("</tr>");
	}

	/**
	 * @param output
	 */
	private void addPageHeader(StringBuffer output) {
		output.append("<!-- header -->");
		output.append("<tr style='height : 10%'>");
		output.append("<td>");
		output.append("<img style=\"padding-bottom: 15px;padding-left: 40px;padding-top: 15px\" alt=\"Hungry Octopus Devouring the Net\" src=\"images/socio-logo-small.png\">");
		output.append("</td>");
		output.append("</tr>");
	}
}
