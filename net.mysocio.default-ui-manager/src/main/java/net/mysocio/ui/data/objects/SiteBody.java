/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class SiteBody extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5586460439566571317L;
	private static final String CATEGORY = "SiteBody";
	public SiteBody(){
		setCategory(CATEGORY);
	}
}
