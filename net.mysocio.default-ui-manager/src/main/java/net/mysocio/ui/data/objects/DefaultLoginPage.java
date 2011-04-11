/**
 * 
 */
package net.mysocio.ui.data.objects;

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
		output.append("<div class='login'>");
		output.append("<form onsubmit=\"commitForm(this); return false;\">");
		output.append("Email <input type=\"text\" name=\"email\">");
		output.append("<input type=\"hidden\" name=\"identifier\" value=\"email\">");
		output.append("<input type=\"hidden\" name=\"command\" value=\"login\">");
		output.append("</form>");
		output.append("</div>");
		return output.toString();
	}
}
