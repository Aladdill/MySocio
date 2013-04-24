/**
 * 
 */
package net.mysocio.connection.readers;

import net.mysocio.data.IUniqueObject;
import net.mysocio.data.NamedObject;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public abstract class Source extends NamedObject implements IUniqueObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 133410971632390235L;
	private String url;
	
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

	public abstract void createRoute(String to) throws Exception;
	
	public abstract void removeRoute(String userId) throws Exception;

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUniqueObject#getUniqueFieldName()
	 */
	@Override
	public String getUniqueFieldName() {
		return "url";
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUniqueObject#getUniqueFieldValue()
	 */
	@Override
	public Object getUniqueFieldValue() {
		return getUrl();
	}
}
