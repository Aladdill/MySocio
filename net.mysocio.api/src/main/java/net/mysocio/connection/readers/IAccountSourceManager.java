/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.List;
import java.util.Map;

import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public interface IAccountSourceManager extends ISourceManager {
	public Map<String, List<String>> orderMessagesByContactsTags(ISource source, List<IMessage> messages); 
}
