/**
 * 
 */
package net.mysocio.data.accounts.lj;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.lj.LjSource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class LjAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2747486084487220210L;
	public static final String ACCOUNT_TYPE = "lj";
	private List<LjFriend> friends = new ArrayList<LjFriend>();

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getSources()
	 */
	@Override
	public List<Source> getSources() {
		List<Source> sources = new ArrayList<Source>();
		for (LjFriend friend : friends) {
			LjSource source = new LjSource();
			source.setUrl(friend.getUrl());
			source.setName(friend.getName());
			SocioTag tag = new SocioTag();
			tag.setIconType("lj.icon");
			tag.setValue("lj.tag");
			tag.setUniqueId("lj.tag");
			source.addTag(tag);
			sources.add(source);
		}
		return sources;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getIconUrl()
	 */
	@Override
	public String getIconUrl() {
		return "lj.icon.account";
	}

	public List<LjFriend> getFriends() {
		return friends;
	}
	
	public void setFriends(List<LjFriend> friends) {
		this.friends = friends;
	}
	public void addFriend(LjFriend friend) {
		this.friends.add(friend);
	}
	
	
}
