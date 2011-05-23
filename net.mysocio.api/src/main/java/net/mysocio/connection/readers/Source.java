/**
 * 
 */
package net.mysocio.connection.readers;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public abstract class Source extends NamedObject implements ISource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 133410971632390235L;
	@Persistent(nullValue=NullValue.DEFAULT)
	protected Long lastMessageId;
	@NotPersistent
	private boolean visible = true;
	
	private String url;
	
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
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
