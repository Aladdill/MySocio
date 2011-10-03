/**
 * 
 */
package net.mysocio.connection.readers.lj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mysocio.data.SocioContact;
import net.mysocio.data.SocioTag;
import net.mysocio.data.contacts.IContact;
import net.mysocio.data.management.DataManagerFactory;

import org.katkov.lj.ClientsFactory;
import org.katkov.lj.ConvenientClient;
import org.katkov.lj.xmlrpc.results.Friend;
import org.katkov.lj.xmlrpc.results.FriendGroup;
import org.katkov.lj.xmlrpc.results.UserData;

/**
 * @author Aladdin
 *
 */
public class LJDataReader {
	private static final int TIMEOUT = 50000;
	private UserData login;
	private List<LjSource> friends = new ArrayList<LjSource>();
	private FriendGroup[] friendgroups;
	
	public void init(String userName, String password){
		ConvenientClient ljConvenientClient = ClientsFactory.getLJConvenientClient();
		this.login = ljConvenientClient.login(userName,password, TIMEOUT);
		Friend[] friends = ljConvenientClient.getFriends(TIMEOUT);
		for (Friend friend : friends) {
			int groupmask = friend.getGroupmask();
			LjSource source = new LjSource(friend.getUsername(), friend.getFullname(), groupmask);
			this.friends.add(source);
//			DataManagerFactory.getDataManager().createSource(source);
		}
		this.friendgroups = login.getFriendgroups();
		tagSources();
	}
	
	public Set<IContact> getLJFriends(){
		Set<IContact> contacts = new HashSet<IContact>();
		//Put friends in groups
		for (LjSource source : this.friends) {
			SocioContact friendContact = new SocioContact();
			friendContact.setName(source.getName());
			friendContact.addSource(source);
			DataManagerFactory.getDataManager().saveObject(friendContact);
			contacts.add(friendContact);
		}
		return contacts;
	}
	private void tagSources(){
/*		Map<Integer, SocioTag> tags = new HashMap<Integer, SocioTag>();
		//Get user friends groups
		for (FriendGroup group : this.friendgroups){
			Integer lgGroupMask = 1 << group.getId();
			SocioTag tag = new SocioTag();
			tag.setName(group.getName());
			tag = DataManagerFactory.getDataManager().createTag(tag);
			tags.put(lgGroupMask, tag);
		}
		for (LjSource source : this.friends) {
			int groupmask = source.getGroupMask();
			// Maximum size of groups is 32, so it's bearable run.
			for (Integer group : tags.keySet()) {
				if ((groupmask&group) != 0){
					source.addTag(tags.get(group));
				}
			}
			DataManagerFactory.getDataManager().saveObject(source);
		}*/
	}
}
