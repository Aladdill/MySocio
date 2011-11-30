/**
 * 
 */
package net.mysocio.authentication.test;

import java.util.Collections;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(detachable="true")
public class TestSource extends Source {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7554040259130079598L;

	public ISourceManager getManager() {
		return new TestSourceManager();
	}
	
	public List<SocioTag> getDefaultTags() {
		return Collections.emptyList();
	}
}
