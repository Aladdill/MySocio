/**
 * 
 */
package net.mysocio.connection.facebook;

import java.util.Collections;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.messages.facebook.FacebookMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookSource extends Source {
	private FacebookAccount account; 

	/**
	 * 
	 */
	private static final long serialVersionUID = 4679653964230964105L;

	public Class<?> getMessageClass() {
		return FacebookMessage.class;
	}

	public ISourceManager getManager() {
		return FacebookSourceManager.getInstance();
	}

	public FacebookAccount getAccount() {
		return account;
	}

	public void setAccount(FacebookAccount account) {
		this.account = account;
	}
	
	public List<SocioTag> getDefaultTags() {
		return Collections.emptyList();
	}
}
