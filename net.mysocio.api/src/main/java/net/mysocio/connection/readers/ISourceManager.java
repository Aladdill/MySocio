/**
 * 
 */
package net.mysocio.connection.readers;

import java.util.List;

import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public interface ISourceManager {
	public List<IMessage> getLastMessages(ISource source, Long from, Long to) throws Exception;
	public List<IMessage> getFirstBulkOfMessages(ISource source) throws Exception;
}
