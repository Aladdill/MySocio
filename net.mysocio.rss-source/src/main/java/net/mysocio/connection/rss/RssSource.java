/**
 * 
 */
package net.mysocio.connection.rss;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.messages.rss.RssMessage;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class RssSource extends Source {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3623303809928356829L;

	public Class<?> getMessageClass() {
		return RssMessage.class;
	}

	public ISourceManager getManager() {
		return new RssSourceManager();
	}

	public List<SocioTag> getDefaultTags() {
		List<SocioTag> tags = new ArrayList<SocioTag>();
		SocioTag tag = new SocioTag();
		tag.setIconType("rss.icon");
		tag.setValue(getName());
		tags.add(tag);
		SocioTag tag1 = new SocioTag();
		tag1.setIconType("rss.icon");
		tag1.setValue("RSS");
		tags.add(tag1);
		return tags;
	}
}
