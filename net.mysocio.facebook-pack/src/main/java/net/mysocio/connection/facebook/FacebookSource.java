/**
 * 
 */
package net.mysocio.connection.facebook;

import java.util.Collections;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.messages.facebook.FacebookMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class FacebookSource extends AccountSource {
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

	public List<SocioTag> getDefaultTags() {
		return Collections.emptyList();
	}
}
