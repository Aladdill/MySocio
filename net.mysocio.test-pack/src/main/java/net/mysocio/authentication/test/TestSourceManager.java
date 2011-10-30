/**
 * 
 */
package net.mysocio.authentication.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.ISource;
import net.mysocio.connection.readers.ISourceManager;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.messages.IMessage;

/**
 * @author Aladdin
 *
 */
public class TestSourceManager implements ISourceManager {

	/* (non-Javadoc)
	 * @see net.mysocio.connection.readers.ISourceManager#getLastMessages(net.mysocio.connection.readers.ISource, java.lang.Long, java.lang.Long)
	 */
	public List<IMessage> getLastMessages(ISource source, Long from, Long to) {
		List<IMessage> messages = new ArrayList<IMessage>();
		for (int i = 1; i <= 10; i++){
			messages.add(createShortTestMesssage(i, source));
		}
		messages.add(createLongTestMessage(source));
		return messages;
	}
	
	/**
	 * @return
	 */
	private static TestMessage createLongTestMessage(ISource source) {
		TestMessage message = new TestMessage();
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title");
		message.setText(DefaultResourcesManager.getResource(new Locale("ru"), "mesage.test.long"));
		message.setUniqueId("http://aladdill.livejournal.com/207656.html");
		return message;
	}

	/**
	 * @param i 
	 * @return
	 */
	private static TestMessage createShortTestMesssage(int i, ISource source) {
		TestMessage message = new TestMessage();
		message.setUniqueId(source.getUrl() + "Test message Title"  + i);
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title " + i);
		message.setText(DefaultResourcesManager.getResource(new Locale("ru"), "mesage.test.short"));
		return message;
	}
}
