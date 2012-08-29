/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.SocioTag;
import net.mysocio.data.messages.GeneralMessage;
import net.mysocio.data.messages.UnreaddenMessage;

import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractMessageProcessor implements Processor{
	protected List<SocioTag> tags = new ArrayList<SocioTag>();
	
	private String to;
	
	public void addTag(SocioTag tag){
		tags.add(tag);
	}
	
	public void addTags(List<SocioTag> tags){
		this.tags.addAll(tags);
	}
	
	protected void addMessageForTag(ProducerTemplate producerTemplate,
			GeneralMessage message, SocioTag tag) {
		UnreaddenMessage unreaddenMessage = new UnreaddenMessage();
		unreaddenMessage.setMessageDate(message.getDate());
		unreaddenMessage.setMessage(message);
		unreaddenMessage.setTag(tag);
		producerTemplate.sendBody(to,unreaddenMessage);
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
