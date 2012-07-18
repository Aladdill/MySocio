/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.NamedObject;
import net.mysocio.data.SocioTag;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public abstract class Source extends NamedObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 133410971632390235L;
	private String url;
	@Reference
	private List<SocioTag> tags = new ArrayList<SocioTag>();
	
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
	
	public void addTag(SocioTag tag) {
		this.tags.add(tag);
	}
	
	public abstract void createRoute(String to) throws Exception;
}
