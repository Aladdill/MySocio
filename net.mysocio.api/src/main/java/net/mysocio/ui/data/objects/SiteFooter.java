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
public abstract class SiteFooter extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6196686443420179752L;
	private static final String CATEGORY = "SiteFooter";
	public SiteFooter(){
		setCategory(CATEGORY);
	}
}
