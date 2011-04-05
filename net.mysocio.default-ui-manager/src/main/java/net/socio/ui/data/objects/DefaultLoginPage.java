/**
 * 
 */
package net.socio.ui.data.objects;

/**
 * @author gurfinke
 *
 */
public class DefaultLoginPage extends SiteBody {
	private static final String NAME = "DefaultLoginPage";
	/**
	 * 
	 */
	public DefaultLoginPage() {
		super();
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		StringBuffer output = new StringBuffer();
		output.append("<div style='position: absolute; top: 50%; left: 50%; margin-left: -200px'>");
		output.append("<form action=\"login\">");
		output.append("Email <input type=\"text\" name=\"email\">");
		output.append("<input type=\"hidden\" name=\"identifier\" value=\"email\">");
		output.append("</form>");
		output.append("</div>");
		return output.toString();
	}
}
