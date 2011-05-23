/**
 * 
 */
package net.mysocio.sources.rss;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.Source;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class RssSource extends Source {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3623303809928356829L;
}
