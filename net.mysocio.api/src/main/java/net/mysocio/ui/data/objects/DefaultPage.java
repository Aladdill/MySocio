/**
 * 
 */
package net.mysocio.ui.data.objects;

import javax.jdo.annotations.PersistenceCapable;


/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")


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
