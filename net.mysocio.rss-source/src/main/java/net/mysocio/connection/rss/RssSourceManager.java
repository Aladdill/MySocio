/**
 * 
 */
package net.mysocio.connection.rss;

import java.util.List;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public class RssSourceManager implements ISourceManager {

	/* (non-Javadoc)
	 * @see net.mysocio.connection.readers.ISourceManager#getLastMessages(net.mysocio.connection.readers.ISource, java.lang.Long, java.lang.Long)
	 */
	public List<IMessage> getLastMessages(ISource source, Long from, Long to)
			throws Exception {
		return DataManagerFactory.getDataManager().getSourceAwareMessages(source.getId(), from, to);
	}

	public List<IMessage> getFirstBulkOfMessages(ISource source)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
