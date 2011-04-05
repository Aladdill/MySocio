/**
 * 
 */
package net.socio.ui.data.objects;


/**
 * @author Aladdin
 *
 */
public class DefaultHeader extends HtmlHeader {
	private static final String NAME = "DefaultHtmlHeader";
	private static final String TITLE = "default.title";
	private DefaultCssImports cssImports = new DefaultCssImports();
	private DefaultJSImports jsImports = new DefaultJSImports();

	public DefaultHeader(){
		setName(NAME);
		addTextLabel(TITLE);
		addInnerObject(cssImports, 1);
		addInnerObject(jsImports, 1);
	}

	@Override
	protected String getInnerHtml() {
		return "<title>"+ getTag(TITLE) + "</title>" + cssImports.getObjectTag(1) + jsImports.getObjectTag(1);
	}
}
