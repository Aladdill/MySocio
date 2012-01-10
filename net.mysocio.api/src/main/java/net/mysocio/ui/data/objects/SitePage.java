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


public abstract class SitePage extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8199613805727758867L;
	private static final String CATEGORY = "SitePage";

	public SitePage(){
		setCategory(CATEGORY);
	}
}
