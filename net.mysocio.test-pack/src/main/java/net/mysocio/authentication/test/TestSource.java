/**
 * 
 */
package net.mysocio.authentication.test;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class TestSource extends Source {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8474042563237153697L;

	public ISourceManager getManager() {
		return new TestSourceManager();
	}
}
