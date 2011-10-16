/**
 * 
 */
package net.mysocio.ui.data.objects;

import net.mysocio.data.ui.UiObject;

/**
 * @author Aladdin
 *
 */
public abstract class SitePage extends UiObject {
	private static final String CATEGORY = "SitePage";

	public SitePage(){
		setCategory(CATEGORY);
	}
}
