/**
 * 
 */
package net.mysocio.data.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.ITagedObject;
import net.mysocio.data.SocioObject;
import net.mysocio.data.SocioTag;
import net.mysocio.data.contacts.IContact;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public abstract class Account extends SocioObject implements ITagedObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7561557374532619473L;
	private String userName =  new String();
	private String accountUniqueId = new String();
	private String userId = new String();
	private String userpicUrl = new String();
	private String email = new String();
	private List<SocioTag> tags = new ArrayList<SocioTag>();
	private Map<String, IContact> contacts = new HashMap<String, IContact>();
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public abstract String getAccountType();

	public String getAccountUniqueId() {
		return accountUniqueId;
	}

	public void setAccountUniqueId(String accountUniqueId) {
		this.accountUniqueId = accountUniqueId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserpicUrl() {
		return userpicUrl;
	}

	public void setUserpicUrl(String userpicUrl) {
		this.userpicUrl = userpicUrl;
	}
	
	public abstract List<ISource> getSources();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SocioTag> getTags() {
		return tags;
	}

	public void setTags(List<SocioTag> tags) {
		this.tags = tags;
	}

	public Map<String, IContact> getContacts() {
		return contacts;
	}

	public void setContacts(Map<String, IContact> contacts) {
		this.contacts = contacts;
	}
}
