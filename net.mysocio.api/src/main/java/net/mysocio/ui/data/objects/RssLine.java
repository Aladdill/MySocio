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
public class RssLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815419229256440884L;
	public static final String NAME = "RssSource";
	public static final String CATEGORY = "RsstLine";
	/**
	 * 
	 */
	public RssLine() {
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}
	@Override
	public String getPageFile() {
		return "RSS.html";
	}
}
