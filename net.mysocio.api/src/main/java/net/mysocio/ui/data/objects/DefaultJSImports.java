/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class DefaultJSImports extends UiObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8204779782469121506L;

	public DefaultJSImports(){
		setName("DefaultJSimports");
		setCategory("JsImports");
	}

	@Override
	public String getPageFile() {
		return "jsImports.html";
	}
}
