/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class SiteHeader extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5693281979603057363L;
	private static final String CATEGORY = "SiteHeader";
	public SiteHeader(){
		setCategory(CATEGORY);
	}
}
