/**
 * 
 */
package net.socio.ui.data.objects;


/**
 * @author Aladdin
 *
 */
public class DefaultPage extends HtmlPage {
	private static final String NAME = "DefaultHtmlPage";
	public DefaultPage() {
		super(new DefaultBody(), new DefaultHeader());
		setName(NAME);
	}
}
