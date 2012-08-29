/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import net.mysocio.data.ContactsList;

import com.google.code.morphia.annotations.Entity;



/**
 * @author Aladdin
 *
 */
@Entity("friend_lists")
public class FacebookFriendList extends ContactsList{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3949039036481784242L;
	
	private String facebookId;

	/**
	 * @return the facebookId
	 */
	public String getFacebookId() {
		return facebookId;
	}

	/**
	 * @param facebookId the facebookId to set
	 */
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
}
