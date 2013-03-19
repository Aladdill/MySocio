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
		//TODO change this shit
		super("HI, this is beta version, you encountered an error, please send mail to aladdin@mysocio.net with description of your nauty doings.");
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CommandExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
}
