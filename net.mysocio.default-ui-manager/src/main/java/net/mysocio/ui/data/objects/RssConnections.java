/**
 * 
 */
package net.mysocio.ui.data.objects;

/**
 * @author Aladdin
 *
 */
public class RssConnections extends SiteBody {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815419229256440884L;
	private static final String NAME = "RssConnectionsPage";
	/**
	 * 
	 */
	public RssConnections() {
		super();
		setName(NAME);
	}
	@Override
	public String getHtmlTemplate() {
		StringBuffer output = new StringBuffer();
		output.append("<div class='fullSize'>");
		output.append("<div id='RssList' class='RssList'>");
		output.append("</div>");
		output.append("<form onsubmit=\"commitForm(this, {}); return false;\">");
		output.append("Rss URL <input type=\"text\" name=\"url\">");
		output.append("<input type=\"hidden\" name=\"command\" value=\"addRssFeed\">");
		output.append("</form>");
		output.append("</div>");
		return output.toString();
	}
}
