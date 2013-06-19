/**
 * 
 */
package net.mysocio.connection.vkontakte;

import java.text.ParseException;

import net.mysocio.data.management.camel.UserMessageProcessor;
import net.mysocio.data.messages.vkontakte.VkontakteMessage;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jmkgreen.morphia.annotations.Transient;

/**
 * @author Aladdin
 *
 */
public class VkontakteInputProcessor extends UserMessageProcessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914368846859623850L;
	@Transient
	private static final Logger logger = LoggerFactory
			.getLogger(VkontakteInputProcessor.class);
	private static final long MONTH = 30*24*3600l;
	private Long lastUpdate = 0l;
	private String token;
	private String accountId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param fs 
	 * @param element
	 * @throws ParseException
	 */
	private VkontakteMessage parseVkontakteMessage(String post) {
		VkontakteMessage message = new VkontakteMessage();
		return message;
	}
	
	public void process() throws Exception {
		long to = System.currentTimeMillis();
		long from = lastUpdate;
		from = to - MONTH;
		lastUpdate = to;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VkontakteInputProcessor other = (VkontakteInputProcessor) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
