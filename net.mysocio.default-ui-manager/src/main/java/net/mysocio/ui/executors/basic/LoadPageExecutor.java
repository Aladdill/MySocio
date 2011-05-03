/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.data.objects.SiteBody;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
public class LoadPageExecutor implements ICommandExecutor {
	private SiteBody page;

	public LoadPageExecutor(SiteBody page) {
		super();
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		DefaultUiManager uiManager = new DefaultUiManager();
		return uiManager.getPage(page,connectionData.getUser());
	}
}
