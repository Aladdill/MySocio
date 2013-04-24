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
