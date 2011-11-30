/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.facebook.FacebookSource;
import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.Oauth2Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
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
	public List<Source> getSources() {
		List<Source> sources = new ArrayList<Source>();
		FacebookSource source = new FacebookSource();
		source.setAccount(this);
		source.setName(getUserName());
		sources.add(source);
		return sources;
	}

	public List<SocioTag> getDefaultTags() {
		List<SocioTag> tags = new ArrayList<SocioTag>();
		SocioTag tag = new SocioTag();
		tag.setValue("facebook.tag");
		tag.setIconType("facebook.icon.general");
		tags.add(tag);
		SocioTag accTag = new SocioTag();
		accTag.setValue(getUserName());
		accTag.setIconType("facebook.icon.account");
		tags.add(accTag);
		return tags;
	}

	@Override
	public String getIconUrl() {
		return "facebook.icon.account";
	}
}
