/**
 * 
 */
package net.mysocio.connection.readers;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@Entity(name = "sources")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Source extends NamedObject implements ISource {
	protected Long lastMessageId;
	@Transient
	private boolean visible = true;
	
	public Source() {
		super();
	}

	/**
	 * @return the lastMessageId
	 */
	public Long getLastMessageId() {
		return lastMessageId;
	}

	/**
	 * @param lastMessageId the lastMessageId to set
	 */
	public void setLastMessageId(Long lastMessageId) {
		this.lastMessageId = lastMessageId;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getUrl() {
		return null;
	}
}
