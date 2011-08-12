/**
 * 
 */
package net.mysocio.data.accounts;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.data.SocioObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public abstract class Account extends SocioObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7561557374532619473L;
	@Persistent
	private String userName =  new String();
	@Persistent
	private String accountUniqueId = new String();
	@Persistent
	private String userId = new String();
	

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
}
