/**
 * 
 */
package net.mysocio.authentication.test;

import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;

/**
 * @author Aladdin
 *
 */
public class TestSource extends Source {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8474042563237153697L;

	@Override
	public ISourceManager getManager() {
		return new TestSourceManager();
	}
}
