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


public class DefaultCssImports extends UiObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -392689705246157374L;
	
	public DefaultCssImports(){
		setName("DefaultCssimports");
		setCategory("CssImports");
	}

	@Override
	public String getPageFile() {
		return "cssImports.html";
	}
	
}
