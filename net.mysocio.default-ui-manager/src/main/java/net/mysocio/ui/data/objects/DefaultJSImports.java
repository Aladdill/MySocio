/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.IUiObject;
import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public class DefaultJSImports extends UiObject implements IUiObject {
	private static final String LIB_DHTMLX_TREE_CODE_BASE = "lib/dhtmlxTree/codebase/";
	private static final String LIB_DHTMLX_DATA_VIEW_CODE_BASE = "lib/dhtmlxDataView/codebase/";

	public DefaultJSImports(){
		setName("DefaultJSimports");
		setCategory("JsImports");
	}

	@Override
	public String getHtmlTemplate() {
		return "<script src=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "dhtmlxcommon.js\"></script>"+
		"<script src=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "dhtmlxtree.js\"></script>"+
		"<script src=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "ext/dhtmlxtree_start.js\"></script>"+
		"<script src=\""+LIB_DHTMLX_DATA_VIEW_CODE_BASE+"dhtmlxdataview.js\" type=\"text/javascript\"></script>"+
		"<script src=\"http://yui.yahooapis.com/3.2.0/build/yui/yui-min.js\"></script>"+
		"<script src=\"lib/utilities.js\"></script>";
	}
}
