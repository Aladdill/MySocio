/**
 * 
 */
package net.mysocio.data.accounts.lj;

import net.mysocio.data.NamedObject;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("friends")
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
