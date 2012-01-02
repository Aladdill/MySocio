/**
 * 
 */
package net.mysocio.data.messages;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
public abstract class SourceAwareMessage extends GeneralMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6469273493301457353L;
	private String sourceId;
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
