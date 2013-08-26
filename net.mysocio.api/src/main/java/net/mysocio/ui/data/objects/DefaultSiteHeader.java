/**
 * 
 */
package net.mysocio.ui.data.objects;

import com.google.code.morphia.annotations.Entity;


/**
 * @author Aladdin
 *
 */
@Entity("ui_objects")
public class DefaultSiteHeader extends SiteHeader {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5572036245791643898L;
	private static final String NAME = "DefaultSiteHeader";
	public DefaultSiteHeader(){
		setName(NAME);
	}
	@Override
	public String getPageFile() {
		return "header.html";
	}
}
