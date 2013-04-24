/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
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
