/**
 * 
 */
package net.mysocio.data.accounts.lj;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class LjFriend extends NamedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999857199400431354L;
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
