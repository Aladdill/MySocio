/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.IUiObject;
import net.mysocio.data.UiObject;

/**
 * @author Aladdin
 *
 */
public class DefaultCssImports extends UiObject implements IUiObject {
	private static final String LIB_DHTMLX_DATA_VIEW_CODE_BASE = "lib/dhtmlxDataView/codebase/";
	private static final String LIB_DHTMLX_TREE_CODE_BASE = "lib/dhtmlxTree/codebase/";
	
	public DefaultCssImports(){
		setName("DefaultCssimports");
		setCategory("CssImports");
	}

	@Override
	public String getHtmlTemplate() {
		return "<link rel=\"STYLESHEET\" type=\"text/css\" href=\""+LIB_DHTMLX_DATA_VIEW_CODE_BASE+"dhtmlxdataview.css\">" +
		"<link rel=\"stylesheet\" type=\"text/css\" href=\""+ LIB_DHTMLX_TREE_CODE_BASE+ "dhtmlxtree.css\">" +
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/defaultUi.css\">";
	}
	
}
