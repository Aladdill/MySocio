/**
 * 
 */
package net.mysocio.data.management.camel;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.AbstractProcessor;
import net.mysocio.data.SocioTag;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity
public abstract class AbstractMessageProcessor extends AbstractProcessor{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112778445393534126L;

	protected List<SocioTag> tags = new ArrayList<SocioTag>();
	
	private String to;
	
	public void addTag(SocioTag tag){
		tags.add(tag);
	}
	
	public void addTags(List<SocioTag> tags){
		this.tags.addAll(tags);
	}
	
	protected void addMessageForTag(GeneralMessage message, SocioTag tag) throws Exception {
		UnreaddenMessage unreaddenMessage = new UnreaddenMessage();
		unreaddenMessage.setMessageDate(message.getDate());
		unreaddenMessage.setMessage(message);
		unreaddenMessage.setMessageId(message.getId().toString());
		unreaddenMessage.setTag(tag);
		DataManagerFactory.getDataManager().sendPackageToRoute(to, unreaddenMessage);
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
