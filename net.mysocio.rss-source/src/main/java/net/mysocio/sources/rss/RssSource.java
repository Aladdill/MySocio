/**
 * 
 */
package net.mysocio.sources.rss;

import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.rss.RssMessage;

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

	public Class<?> getMessageClass() {
		return RssMessage.class;
	}

	public ISourceManager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SocioTag> getDefaultTags() {
		// TODO Auto-generated method stub
		return null;
	}
}
