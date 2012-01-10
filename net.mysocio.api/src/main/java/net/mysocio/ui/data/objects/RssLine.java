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
