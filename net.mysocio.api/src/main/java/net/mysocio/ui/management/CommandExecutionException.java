/**
 * 
 */
package net.mysocio.ui.management;

/**
 * @author Aladdin
 *
 */
public class CommandExecutionException extends Exception {

	public static final String BETA_ERROR = "HI, this is beta version, you encountered an error, please send mail to aladdin@mysocio.net with description of your nauty doings.";
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
