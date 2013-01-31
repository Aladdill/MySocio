/**
 * 
 */
package net.mysocio.connection.facebook;

import net.mysocio.connection.readers.AccountSource;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.facebook.FacebookAccount;
import net.mysocio.data.management.CamelContextManager;
import net.mysocio.data.messages.facebook.FacebookMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("sources")
public class FacebookSource extends AccountSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679653964230964105L;

	public Class<?> getMessageClass() {
		return FacebookMessage.class;
	}

	public void createRoute(String to) throws Exception {
		FacebookInputProcessor processor = new FacebookInputProcessor();
		processor.setTo(to);
		FacebookAccount account = (FacebookAccount)getAccount();
		processor.setToken(account.getToken());
		SocioTag tag = new SocioTag();
		tag.setValue("facebook.tag");
		tag.setIconType("facebook.icon.general");
		processor.addTag(tag);
		SocioTag tag1 = new SocioTag();
		tag1.setValue(account.getUserName());
		tag1.setIconType("facebook.icon.general");
		processor.addTag(tag1);
		CamelContextManager.addRoute("timer://" + getId() + "?fixedRate=true&period=60s", processor, null, 0l);
	}
}