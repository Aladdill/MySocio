/**
 * 
 */
package net.mysocio.ui.management;

/**
 * @author Aladdin
 *
 */
public class CommandExecutionException extends Exception {

	public static final String BETA_ERROR = "dialog.default.beta.error";
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
		//TODO change this shit
		super(BETA_ERROR);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CommandExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
}
