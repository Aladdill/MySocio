/**
 * 
 */
package net.mysocio.ui.data.objects;

import com.github.jmkgreen.morphia.annotations.Entity;


/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class DefaultPage extends HtmlPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -266893846002028384L;
	private static final String NAME = "DefaultHtmlPage";
	public DefaultPage() {
		super(new DefaultBody(), new DefaultHeader());
		setName(NAME);
	}
}
