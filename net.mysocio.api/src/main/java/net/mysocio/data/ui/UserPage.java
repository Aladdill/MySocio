/**
 * 
 */
package net.mysocio.data.ui;

import com.google.code.morphia.annotations.Entity;

import net.mysocio.data.SocioObject;

/**
 * @author Oslocomp
 *
 */
@Entity("users_pages")
public class UserPage extends SocioObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4009468009832372193L;
	private String userId; 
	private String pageKey;
	private String pageHTML;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPageKey() {
		return pageKey;
	}
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}
	public String getPageHTML() {
		return pageHTML;
	}
	public void setPageHTML(String pageHTML) {
		this.pageHTML = pageHTML;
	}
}
