/**
 * 
 */
package net.mysocio.ui.management;

/**
 * @author Aladdin
 *
 */
public class CommandExecutionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1362152042576316917L;

	/**
	 * 
	 */
	public CommandExecutionException() {
	}

	/**
	 * @param message
	 */
	public CommandExecutionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CommandExecutionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CommandExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
}
