/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.ui.management.CommandExecutionException;

/**
 * @author Aladdin
 *
 */
public class NotValidRssUrlException extends CommandExecutionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2251022850344549732L;

	/**
	 * 
	 */
	public NotValidRssUrlException() {
	}

	/**
	 * @param message
	 */
	public NotValidRssUrlException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NotValidRssUrlException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotValidRssUrlException(String message, Throwable cause) {
		super(message, cause);
	}
}
