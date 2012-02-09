/**
 * 
 */
package net.mysocio.authentication.lj;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.lj.LjAccount;
import net.mysocio.data.accounts.lj.LjFriend;
import net.mysocio.utils.rss.AddingRssException;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.opml.Opml;
import com.sun.syndication.feed.opml.Outline;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.impl.OPML20Parser;

/**
 * @author Aladdin
 *
 */
public class LjAuthenticationManager implements IAuthenticationManager {
	private static final String NO_USERNAME_FOUND_FOR_LJ_ACCOUNT = "No username found for LJ account.";
	private static final String LJ_AUTHENTICATION_IS_NOT_YET_IMPLEMENTED = "LJ authentication is not yet implemented.";
	private static final Logger logger = LoggerFactory.getLogger(LjAuthenticationManager.class);
	/* (non-Javadoc)
	 * @see net.mysocio.data.IAuthenticationManager#getRequestUrl()
	 */
	@Override
	public String getRequestUrl() {
		logger.error(LJ_AUTHENTICATION_IS_NOT_YET_IMPLEMENTED);
		throw new UnsupportedOperationException(LJ_AUTHENTICATION_IS_NOT_YET_IMPLEMENTED);
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IAuthenticationManager#getAccount(net.mysocio.data.IConnectionData)
	 */
	@Override
	public Account getAccount(IConnectionData connectionData) throws Exception {
		String username = connectionData.getRequestParameter("username");
		if (username == null){
			logger.error(NO_USERNAME_FOUND_FOR_LJ_ACCOUNT);
			throw new IllegalArgumentException(NO_USERNAME_FOUND_FOR_LJ_ACCOUNT);
		}
		LjAccount ljAccount = new LjAccount();
		
		try {
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new InputStreamReader(new URL("http://" + username + ".livejournal.com/data/rss").openStream(),
					"UTF-8"));
			ljAccount.setUserpicUrl(feed.getImage().getUrl());
			
		} catch (Exception e) {
			logger.error("Error verifying LJ Account " + username, e);
			throw new AddingRssException("LJ Account " + username
					+ "is not a valid Account.", e);
		}
		
		ljAccount.setAccountUniqueId(username);
		URL feedURL = new URL("http://www.livejournal.com/tools/opml.bml?user=" + username);
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(feedURL.openStream());
		OPML20Parser parser = new OPML20Parser();
		Opml feed = (Opml) parser.parse(document, true);
		List<Outline> outlines = (List<Outline>) feed.getOutlines();
		for (Outline outline : outlines) {
			List<Outline> children = outline.getChildren();
			if (children.size() > 0) {
				for (Outline child : children) {
					LjFriend friend = new LjFriend();
					friend.setName(child.getText());
					friend.setUrl(child.getXmlUrl());
					ljAccount.addFriend(friend);
				}
			}
		}
		return ljAccount;
	}
}
