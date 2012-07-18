/**
 * 
 */
package net.mysocio.authentication.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.data.messages.GeneralMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public class TestSource extends Source {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7554040259130079598L;

	/* (non-Javadoc)
	 * @see net.mysocio.connection.readers.ISourceManager#getLastMessages(net.mysocio.connection.readers.ISource, java.lang.Long, java.lang.Long)
	 */
	public List<GeneralMessage> getLastMessages(Source source, Long from, Long to) {
		List<GeneralMessage> messages = new ArrayList<GeneralMessage>();
		for (int i = 1; i <= 10; i++){
			messages.add(createShortTestMesssage(i, source));
		}
		messages.add(createLongTestMessage(source));
		return messages;
	}
	
	/**
	 * @return
	 */
	private static TestMessage createLongTestMessage(Source source) {
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
	private static TestMessage createShortTestMesssage(int i, Source source) {
		TestMessage message = new TestMessage();
		message.setUniqueId(source.getUrl() + "Test message Title"  + i);
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title " + i);
		message.setText(DefaultResourcesManager.getResource(new Locale("ru"), "mesage.test.short"));
		return message;
	}

	public List<GeneralMessage> getFirstBulkOfMessages(Source source)
			throws Exception {
		return Collections.emptyList();
	}

	public void createRoute(String to) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
