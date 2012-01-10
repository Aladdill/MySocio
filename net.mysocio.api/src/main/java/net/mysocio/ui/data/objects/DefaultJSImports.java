/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")


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
