/**
 * 
 */
package net.mysocio.data.management;

/**
 * @author gurfinke
 *
 */
public class DuplicateMySocioObjectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 216205460430305307L;

	/**
	 * 
	 */
	public DuplicateMySocioObjectException() {
	}

	/**
	 * @param arg0
	 */
	public DuplicateMySocioObjectException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public DuplicateMySocioObjectException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DuplicateMySocioObjectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
