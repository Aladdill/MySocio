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
public class DefaultLoginPage extends SiteBody {
	/**
	 * 
	 */
	private static final long serialVersionUID = 157601033356844407L;
	private static final String NAME = "DefaultLoginPage";
	/**
	 * 
	 */
	public DefaultLoginPage() {
		super();
		setName(NAME);
		addTextLabel("login.page.why.mysocio.header");
		addTextLabel("login.page.why.mysocio.text");
		addTextLabel("login.page.new.to.mysocio.header");
		addTextLabel("login.page.new.to.mysocio.text");
		addTextLabel("login.page.we.are.open.source.header");
		addTextLabel("login.page.we.are.open.source.text");
		addTextLabel("login.page.help.us.help.you.header");
		addTextLabel("login.page.help.us.help.you.text");
		addTextLabel("login.page.privacy.header");
		addTextLabel("login.page.privacy.text");
		addTextLabel("login.page.moto.upper");
		addTextLabel("login.page.moto.lower");
	}
	@Override
	public String getPageFile() {
		return "loginPage.html";
	}
}
