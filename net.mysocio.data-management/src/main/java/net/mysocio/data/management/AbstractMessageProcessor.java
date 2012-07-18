/**
 * 
 */
package net.mysocio.data.management;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.data.SocioTag;

import org.apache.camel.Processor;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractMessageProcessor implements Processor{
	protected List<SocioTag> tags = new ArrayList<SocioTag>();
	
	public void addTag(SocioTag tag){
		tags.add(tag);
	}
}
