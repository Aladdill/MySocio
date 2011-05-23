/**
 * 
 */
package net.mysocio.data.lj;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.rss.RssMessage;

/**
 * @author gurfinke
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class LjMessage extends RssMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2630993837012989617L;

	public LjMessage(String link) {
		super(link);
	}
}
