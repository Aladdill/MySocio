/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.mysocio.connection.rss.RssSource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.messages.rss.RssMessagesRidingBean;
import net.mysocio.ui.data.objects.RssConnections;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author Aladdin
 *
 */
public class AddRssFeedExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(AddRssFeedExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		String url = connectionData.getRequestParameter("url");
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;
		try {
			feed = input.build(new XmlReader(new URL(url)));
		} catch (Exception e) {
			logger.info("Error verifying feed" + url, e);
			throw new NotValidRssUrlException("URL " + url + "is not a valid RSS url.");
		}
		RssSource source = new RssSource();
		source.setUrl(url);
		source.setName(feed.getTitle());
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioUser user = connectionData.getUser();
		source = (RssSource)dataManager.createSource(source);
		List<RssMessagesRidingBean> beans = new ArrayList<RssMessagesRidingBean>();
		RssMessagesRidingBean bean = new RssMessagesRidingBean();
		bean.setId(source.getId());
		bean.setUrl(source.getUrl());
		beans.add(bean);
		user.addSource(source);
		AbstractUiManager uiManager = new DefaultUiManager();
		return uiManager.getPage(new RssConnections(),user);
	}
}
