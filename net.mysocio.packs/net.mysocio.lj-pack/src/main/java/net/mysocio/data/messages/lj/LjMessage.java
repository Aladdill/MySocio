/**
 * 
 */
package net.mysocio.data.messages.lj;

import net.mysocio.data.messages.rss.RssMessage;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("messages")
public class LjMessage extends RssMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2350549969402813650L;
	
	private String userpic;

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
}
