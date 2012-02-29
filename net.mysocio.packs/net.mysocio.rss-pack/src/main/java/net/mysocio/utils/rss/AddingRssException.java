/**
 * 
 */
package net.mysocio.utils.rss;


/**
 * @author Aladdin
 *
 */
public class AddingRssException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2251022850344549732L;

	/**
	 * 
	 */
	public AddingRssException() {
	}

	/**
	 * @param message
	 */
	public AddingRssException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AddingRssException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AddingRssException(String message, Throwable cause) {
		super(message, cause);
	}
}
