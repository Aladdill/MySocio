/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

import net.mysocio.connection.facebook.FacebookSource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.Oauth2Account;

/**
 * @author Aladdin
 *
 */
@Entity("accounts")
public class FacebookAccount extends Oauth2Account {

	public static final String ACCOUNT_TYPE = "facebook";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6186811184252889740L;
	@Reference
	private List<FacebookFriendList> friendLists = new ArrayList<FacebookFriendList>();
	@Reference
	private List<FacebookFriend> friends = new ArrayList<FacebookFriend>();
	
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
	public List<Source> getSources() {
		List<Source> sources = new ArrayList<Source>();
		FacebookSource source = new FacebookSource();
		source.setAccount(this);
		source.setName(getUserName());
		SocioTag tag = new SocioTag();
		tag.setValue("facebook.tag");
		tag.setUserId("facebook.tag");
		tag.setIconType("facebook.icon.general");
		source.addTag(tag);
		SocioTag tag1 = new SocioTag();
		tag1.setValue(getUserName());
		tag1.setUserId(getAccountUniqueId());
		tag1.setIconType("facebook.icon.general");
		source.addTag(tag1);
		sources.add(source);
		return sources;
	}

	@Override
	public String getIconUrl() {
		return "facebook.icon.account";
	}
}
