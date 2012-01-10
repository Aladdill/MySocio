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
