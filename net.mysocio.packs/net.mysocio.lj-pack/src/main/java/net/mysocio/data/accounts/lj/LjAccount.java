/**
 * 
 */
package net.mysocio.data.accounts.lj;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.lj.LjSource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.contacts.Contact;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("accounts")
public class LjAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2747486084487220210L;
	public static final String ACCOUNT_TYPE = "lj";

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getSources()
	 */
	@Override
	public List<Source> getSources() {
		List<Source> sources = new ArrayList<Source>();
		List<Contact> contacts = getContacts();
		for (Contact friend : contacts) {
			LjSource source = new LjSource();
			source.setUrl(friend.getUrl());
			source.setName(friend.getName());
			SocioTag tag = new SocioTag();
			tag.setIconType("lj.icon");
			tag.setValue("lj.tag");
			source.addTag(tag);
			SocioTag tag1 = new SocioTag();
			tag1.setIconType("lj.icon");
			tag1.setValue(friend.getName());
			source.addTag(tag1);
			sources.add(source);
		}
		return sources;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getIconUrl()
	 */
	@Override
	public String getIconUrl() {
		return "lj.icon.account";
	}
}