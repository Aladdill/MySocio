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


public class AddRssLine extends UiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815419229256440884L;
	public static final String NAME = "NewRss";
	public static final String CATEGORY = "RsstLine";
	/**
	 * 
	 */
	public AddRssLine() {
		super();
		setName(NAME);
		setCategory(CATEGORY);
	}
	@Override
	public String getPageFile() {
		return "addRSS.html";
	}
}
