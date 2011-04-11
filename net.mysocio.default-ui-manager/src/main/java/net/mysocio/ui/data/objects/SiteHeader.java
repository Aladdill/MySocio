/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class SiteHeader extends UiObject {
	private static final String CATEGORY = "SiteHeader";
	public SiteHeader(){
		setCategory(CATEGORY);
	}
}
