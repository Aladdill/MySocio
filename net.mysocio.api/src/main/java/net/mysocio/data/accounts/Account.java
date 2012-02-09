/**
 * 
 */
package net.mysocio.data.accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDisplayedObject;
import net.mysocio.data.SocioObject;
import net.mysocio.data.contacts.Contact;
import net.mysocio.ui.data.objects.NewAccountLine;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
public abstract class Account extends SocioObject implements IDisplayedObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7561557374532619473L;
	private String userName =  new String();
	private String accountUniqueId = new String();
	private String userId = new String();
	private String userpicUrl = new String();
	private String email = new String();
	private Map<String, Contact> contacts = new HashMap<String, Contact>();
	

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
	
	public abstract List<Source> getSources();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Map<String, Contact> contacts) {
		this.contacts = contacts;
	}
	public abstract String getIconUrl();
	
	@Override
	public String getUiCategory() {
		return NewAccountLine.CATEGORY;
	}

	@Override
	public String getUiName() {
		return NewAccountLine.NAME;
	}
}
