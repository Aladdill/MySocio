/**
 * 
 */
package net.mysocio.data.accounts.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mysocio.connection.facebook.FacebookSource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.ContactsList;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Oauth2Account;
import net.mysocio.data.contacts.Contact;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("accounts")
public class FacebookAccount extends Oauth2Account {

	public static final String ACCOUNT_TYPE = "facebook";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6186811184252889740L;
	
	/* (non-Javadoc)
	 * @see net.mysocio.data.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

	@Override
	public List<Source> getSources() {
		List<Source> sources = new ArrayList<Source>();
		FacebookSource source = new FacebookSource();
		source.setAccount(this);
		source.setName(getUserName());
		source.setUrl("http://graph.facebook.com/" + getAccountUniqueId());
		sources.add(source);
		return sources;
	}

	@Override
	public String getIconUrl() {
		return "facebook.icon.account";
	}

	@Override
	public SocioTag createAccountTagset(UserTags userTags) {
		SocioTag accountTypeTag = createAccountTypeTag(userTags);
		SocioTag sourceTag = userTags.createTag(getAccountUniqueId(), getUserName(), accountTypeTag);
		List<ContactsList> contactsLists = getContactsLists();
		Map<String,SocioTag> friendsTags = new HashMap<String,SocioTag>();
		List<Contact> contacts = getContacts();
		for (Contact contact : contacts) {
			String contactId = contact.getUniqueFieldValue().toString();
			SocioTag contactTag = userTags.createTag(contactId, contact.getName(), sourceTag);
			friendsTags.put(contactId, contactTag);
		}
		
		for (ContactsList contactsList : contactsLists) {
			SocioTag listTag = userTags.createTag(contactsList.getId().toString(), contactsList.getName(), accountTypeTag);
			List<String> ids = ((FacebookFriendList)contactsList).getIds();
			for (String id : ids) {
				listTag.addChild(friendsTags.get(id));
			}
		}
		return accountTypeTag;
	}
}
