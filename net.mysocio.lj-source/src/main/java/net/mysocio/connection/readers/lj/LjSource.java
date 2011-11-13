/**
 * 
 */
package net.mysocio.connection.readers.lj;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.rss.RssSource;


/**
 * @author gurfinke
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class LjSource extends RssSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2083873860231648163L;
	private String username;
	private int groupMask;
	
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
}
