/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;
import net.mysocio.data.SocioTag;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Source extends NamedObject implements ISource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 133410971632390235L;
	private String url;
	private List<SocioTag> tags;
	
	public Source() {
		super();
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

	public List<SocioTag> getTags() {
		return tags;
	}

	public void setTags(List<SocioTag> tags) {
		this.tags = tags;
	}
}
