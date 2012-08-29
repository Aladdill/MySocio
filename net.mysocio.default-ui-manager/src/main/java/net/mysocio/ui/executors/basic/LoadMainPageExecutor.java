/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.data.objects.DefaultSiteBody;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class LoadMainPageExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(LoadMainPageExecutor.class);

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		AbstractUiManager uiManager = new DefaultUiManager();
		String pageHtml = "";
		try {
			pageHtml = uiManager.getPage(DefaultSiteBody.CATEGORY, DefaultSiteBody.NAME, connectionData.getUserId());
		} catch (CorruptedDataException e) {
			logger.error("Failed showing main page.",e);
			throw new CommandExecutionException(e);
		}
		String userId = connectionData.getUserId();
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioUser user = dataManager.getObject(SocioUser.class, userId);
		pageHtml = pageHtml.replace("userpic.url", user.getMainAccount().getUserpicUrl());
		pageHtml = pageHtml.replace("user.name", user.getName());
		return pageHtml;
	}
}
