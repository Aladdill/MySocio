/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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



/**
 * @author Aladdin
 *
 */
public class DefaultUiManager extends AbstractUiManager {
	private static Map<String, UiObject> defaultPages;
	
	public static void init(){
		defaultPages = new HashMap<String, UiObject>();
		DefaultLoginPage defaultLoginPage = new DefaultLoginPage();
		defaultPages.put(defaultLoginPage.getCategory()+defaultLoginPage.getName(),defaultLoginPage);
		AccountLine accountLine = new AccountLine();
		defaultPages.put(accountLine.getCategory()+accountLine.getName(),accountLine);
		DefaultAccountLine defaultAccountLine = new DefaultAccountLine();
		defaultPages.put(defaultAccountLine.getCategory()+defaultAccountLine.getName(),defaultAccountLine);
		RssLine rssLine = new RssLine();
		defaultPages.put(rssLine.getCategory()+rssLine.getName(),rssLine);
		ContactLine contactLine = new ContactLine();
		defaultPages.put(contactLine.getCategory()+contactLine.getName(),contactLine);
		DefaultMessage defaultMessage = new DefaultMessage();
		defaultPages.put(defaultMessage.getCategory()+defaultMessage.getName(),defaultMessage);
		UserUiMessage userUiMessage = new UserUiMessage();
		defaultPages.put(userUiMessage.getCategory()+userUiMessage.getName(),userUiMessage);
		FacebookUiMessage facebookUiMessage = new FacebookUiMessage();
		defaultPages.put(facebookUiMessage.getCategory()+facebookUiMessage.getName(),facebookUiMessage);
		facebookUiMessage = new FacebookUiCheckinMessage();
		defaultPages.put(facebookUiMessage.getCategory()+facebookUiMessage.getName(),facebookUiMessage);
		facebookUiMessage = new FacebookUiLinkMessage();
		defaultPages.put(facebookUiMessage.getCategory()+facebookUiMessage.getName(),facebookUiMessage);
		facebookUiMessage = new FacebookUiPhotoMessage();
		defaultPages.put(facebookUiMessage.getCategory()+facebookUiMessage.getName(),facebookUiMessage);
		facebookUiMessage = new FacebookUiStatusMessage();
		defaultPages.put(facebookUiMessage.getCategory()+facebookUiMessage.getName(),facebookUiMessage);
		facebookUiMessage = new FacebookUiVideoMessage();
		defaultPages.put(facebookUiMessage.getCategory()+facebookUiMessage.getName(),facebookUiMessage);
		RssUiMessage rssUiMessage = new RssUiMessage();
		defaultPages.put(rssUiMessage.getCategory()+rssUiMessage.getName(),rssUiMessage);
		DefaultSiteBody defaultSiteBody = new DefaultSiteBody();
		defaultPages.put(defaultSiteBody.getCategory()+defaultSiteBody.getName(),defaultSiteBody);
	}
	
	public String getPage(String category, String name, String userId) throws CorruptedDataException {
		String pageKey = category + name;
		IDataManager dataManager = DataManagerFactory.getDataManager();
		String pageHTML = dataManager.getPage(userId, pageKey);
		if (pageHTML == null){
			UiObject page = defaultPages.get(pageKey);
			if (page == null){
				throw new CorruptedDataException("Page: " + category + " " + name + " wasn't found.");
			}
			SocioUser user = dataManager.getObject(SocioUser.class, userId);
			pageHTML = getUiObjectHtml(page , defaultPages , new Locale(user.getLocale()));
			UserPage userPage =  new UserPage();
			userPage.setUserId(userId);
			userPage.setPageHTML(pageHTML);
			userPage.setPageKey(pageKey);
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
