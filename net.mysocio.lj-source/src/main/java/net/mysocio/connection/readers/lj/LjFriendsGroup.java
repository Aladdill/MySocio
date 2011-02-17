/**
 * 
 */
package net.mysocio.connection.readers.lj;

import java.util.List;

import javax.persistence.Entity;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.SourcesGroup;

/**
 * @author Aladdin
 *
 */
@Entity
public class LjFriendsGroup extends SourcesGroup {
	private int lgGroupMask;
	
	public LjFriendsGroup(){
		super();
	}
	public LjFriendsGroup(String name, int lgGroupMask) {
		super();
		setName(name);
		setLgGroupMask(lgGroupMask);
	}
	
	public LjFriendsGroup(String name, int lgGroupMask, List<LjSource> friends) {
		super();
		setName(name);
		setLgGroupMask(lgGroupMask);
		addSources(friends);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLgGroupMask() {
		return lgGroupMask;
	}

	public void setLgGroupMask(int lgGroupMask) {
		this.lgGroupMask = lgGroupMask;
	}
	
	public void addFriend(ISource friend){
		getSources().add(friend);
	}
}
