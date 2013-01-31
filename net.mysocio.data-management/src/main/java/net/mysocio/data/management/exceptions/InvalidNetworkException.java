/**
 * 
 */
package net.mysocio.data.management.exceptions;

/**
 * @author Aladdin
 *
 */
public class InvalidNetworkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027601135136429628L;

	/**
	 * 
	 */
	public InvalidNetworkException() {
	}

	/**
	 * @param message
	 */
	public InvalidNetworkException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidNetworkException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidNetworkException(String message, Throwable cause) {
		super(message, cause);
	}

}
