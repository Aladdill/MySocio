/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.ui.UiObject;
import net.mysocio.data.ui.UserPage;
import net.mysocio.ui.data.objects.AccountLine;
import net.mysocio.ui.data.objects.ContactLine;
import net.mysocio.ui.data.objects.DefaultAccountLine;
import net.mysocio.ui.data.objects.DefaultLoginPage;
import net.mysocio.ui.data.objects.DefaultMessage;
import net.mysocio.ui.data.objects.DefaultSiteBody;
import net.mysocio.ui.data.objects.RssLine;
import net.mysocio.ui.data.objects.UserUiMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiCheckinMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiLinkMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiPhotoMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiStatusMessage;
import net.mysocio.ui.data.objects.facebook.FacebookUiVideoMessage;
import net.mysocio.ui.data.objects.rss.RssUiMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiLinkMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiPhotoMessage;
import net.mysocio.ui.data.objects.vkontakte.VkontakteUiVideoMessage;



/**
 * @author Aladdin
 *
 */
public class DefaultUiManager extends AbstractUiManager {
	private static final Logger logger = LoggerFactory.getLogger(DefaultUiManager.class);
	private static Map<String, UiObject> defaultPages;
	
	public static void init(){
		logger.debug("Initializing UI objects");
		defaultPages = new HashMap<String, UiObject>();
		logger.debug("Initializing basic UI objects");
		UiObject[] basicUiObjects = {new DefaultLoginPage(), new AccountLine(), new DefaultAccountLine(), new RssLine(),
				new ContactLine(), new DefaultMessage(), new UserUiMessage(), new DefaultSiteBody()};
		for (UiObject uiObject : basicUiObjects) {
			defaultPages.put(uiObject.getCategory()+uiObject.getName(),uiObject);
		}
		logger.debug("Initializing facebook UI objects");
		UiObject[] facebookObjects = {new FacebookUiMessage(), new FacebookUiCheckinMessage(), new FacebookUiLinkMessage(),
				new FacebookUiPhotoMessage(), new FacebookUiStatusMessage(), new FacebookUiVideoMessage()};
		for (UiObject uiObject : facebookObjects) {
			defaultPages.put(uiObject.getCategory()+uiObject.getName(),uiObject);
		}
		logger.debug("Initializing vkontakte UI objects");
		UiObject[] vkontakteObjects = {new VkontakteUiMessage(), new VkontakteUiLinkMessage(),
				new VkontakteUiPhotoMessage(), new VkontakteUiVideoMessage()};
		for (UiObject uiObject : vkontakteObjects) {
			defaultPages.put(uiObject.getCategory()+uiObject.getName(),uiObject);
		}
		logger.debug("Initializing RSS UI objects");
		RssUiMessage rssUiMessage = new RssUiMessage();
		defaultPages.put(rssUiMessage.getCategory()+rssUiMessage.getName(),rssUiMessage);
	}
	
	public String getPage(String category, String name, String userId) throws CorruptedDataException {
		String pageKey = category + name;
		logger.debug("Getting UI object " + pageKey);
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String pageHTML = dataManager.getPage(userId, pageKey);
		if (pageHTML == null){
			UiObject page = defaultPages.get(pageKey);
			if (page == null){
				throw new CorruptedDataException("Page: " + category + " " + name + " wasn't found.");
			}
			SocioUser user = dataManager.getObject(SocioUser.class, userId);
			pageHTML = getUiObjectHtml(page , defaultPages , new Locale(user.getLocale()));
			logger.debug("HTML is: " + pageHTML);
//			UserPage userPage =  new UserPage();
//			userPage.setUserId(userId);
//			userPage.setPageHTML(pageHTML);
//			userPage.setPageKey(pageKey);
			try {
				//TODO while working on UI I don't need to save it, remove later 
//				dataManager.saveObject(userPage);
			} catch (Exception e) {
				throw new CorruptedDataException("Impossible situation object doesn't exist, but can't be saved!", e);
			}
		}
		return pageHTML;
	}

	public String getLoginPage(Locale locale) throws CorruptedDataException {
		return getUiObjectHtml(new DefaultLoginPage(), Collections.EMPTY_MAP, locale);
	}
}
