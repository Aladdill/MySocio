/**
 * 
 */
package net.mysocio.ui.management;

/**
 * @author Aladdin
 *
 */
public class UnapprovedUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180641726661515280L;

	/**
	 * 
	 */
	public UnapprovedUserException() {
		super("dialog.login.not.invited");
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnapprovedUserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public UnapprovedUserException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnapprovedUserException(Throwable cause) {
		super(cause);
	}
}
