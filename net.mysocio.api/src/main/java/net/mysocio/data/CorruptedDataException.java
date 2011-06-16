/**
 * 
 */
package net.mysocio.data;

/**
 * @author Aladdin
 *
 */
public class CorruptedDataException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6161560471401807828L;

	public CorruptedDataException() {
		super();
	}

	public CorruptedDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public CorruptedDataException(String message) {
		super(message);
	}

	public CorruptedDataException(Throwable cause) {
		super(cause);
	}
}
