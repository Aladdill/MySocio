/**
 * 
 */
package net.socio.ui.managers.basic;

import net.socio.ui.data.basic.DefaultHeader;
import net.socio.ui.data.basic.HtmlPage;

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
