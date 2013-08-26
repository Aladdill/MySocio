/**
 * 
 */
package net.mysocio.data.accounts.vkontakte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.vkontakte.VkontakteSource;
import net.mysocio.data.ContactsList;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Oauth2Account;
import net.mysocio.data.contacts.Contact;
import net.mysocio.data.messages.GeneralMessage;

import com.google.code.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("accounts")
public class VkontakteAccount extends Oauth2Account {

	public static final String ACCOUNT_TYPE = "vkontakte";
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
		VkontakteSource source = new VkontakteSource();
		source.setAccount(this);
		source.setName(getUserName());
		source.setUrl("http://vk.com/id" + getAccountUniqueId());
		sources.add(source);
		return sources;
	}

	@Override
	public String getIconUrl() {
		return "vkontakte.icon.account";
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
			List<String> ids = ((VkontakteFriendList)contactsList).getIds();
			for (String id : ids) {
				listTag.addChild(friendsTags.get(id));
			}
		}
		return accountTypeTag;
	}

	@Override
	public void postToAccount(String message) throws Exception {
//		String regex = "(http|ftp|https)://[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&amp;:/~+#-]*[\\w@?^=%&amp;/~+#-])?";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(message);
//		if (m.find()){
//			try {
//				facebook.postLink(message, new URL(m.group()));
//				return;
//			} catch (MalformedURLException e) {
//				//url was found, but malformed - tough luck
//			}
//		}
	}

	@Override
	public void like(GeneralMessage message) throws Exception {
	}
}
