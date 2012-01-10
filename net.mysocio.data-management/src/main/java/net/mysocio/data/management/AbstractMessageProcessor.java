/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.SocioTag;
import net.mysocio.data.messages.GeneralMessage;

import org.apache.camel.Processor;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractMessageProcessor implements Processor{
	private List<SocioTag> tags = new ArrayList<SocioTag>();
	
	public void addTag(SocioTag tag){
		tags.add(tag);
	}

	public void addTagsToMessage(GeneralMessage message) {
		for (SocioTag tag : tags) {
			message.addTag(tag);
		}
	}
}
