/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.ContactsList;

import com.github.jmkgreen.morphia.annotations.Entity;



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
	
	private List<String> ids = new ArrayList<String>(); 

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

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
