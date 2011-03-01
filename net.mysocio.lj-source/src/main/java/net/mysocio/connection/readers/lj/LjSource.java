/**
 * 
 */
package net.mysocio.connection.readers.lj;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.Source;

/**
 * @author gurfinke
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class LjSource extends Source {
	private String username;
	private int groupMask;
	private String url;
	
	public LjSource(){
		super();
	}
	public LjSource(String username, String fullname,
			int groupMask) {
		super();
		setUrl("http://www.livejournal.com/users/" + username + "/data/rss");
		setUsername(username);
		setName(fullname);
		setGroupMask(groupMask);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGroupMask() {
		return groupMask;
	}

	public void setGroupMask(int groupMask) {
		this.groupMask = groupMask;
	}
	
	/**
	 * @return the url
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
