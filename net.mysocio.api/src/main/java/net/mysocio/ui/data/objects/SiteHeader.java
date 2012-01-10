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
