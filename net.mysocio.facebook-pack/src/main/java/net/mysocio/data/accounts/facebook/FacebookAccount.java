/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.facebook.FacebookSource;
import net.mysocio.connection.readers.ISource;
import net.mysocio.data.accounts.Oauth2Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookAccount extends Oauth2Account {

	private static final String ACCOUNT_TYPE = "facebook";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6186811184252889740L;
	
	private List<FacebookFriendList> friendLists;
	private List<FacebookFriend> friends;
	
	/* (non-Javadoc)
	 * @see net.mysocio.data.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

	public List<FacebookFriendList> getFriendLists() {
		return friendLists;
	}

	public void setFriendLists(List<FacebookFriendList> friendLists) {
		this.friendLists = friendLists;
	}

	public List<FacebookFriend> getFriends() {
		return friends;
	}

	public void setFriends(List<FacebookFriend> friends) {
		this.friends = friends;
	}

	@Override
	public List<ISource> getSources() {
		List<ISource> sources = new ArrayList<ISource>();
		FacebookSource source = new FacebookSource();
		source.setAccount(this);
		source.setName(getUserName());
		sources.add(source);
		return sources;
	}
}
