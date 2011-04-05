/**
 * 
 */
package net.socio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.ICommandExecutor;
import net.socio.ui.data.objects.SiteBody;
import net.socio.ui.managers.basic.DefaultUiManager;

/**
 * @author gurfinke
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
	public String execute(IConnectionData connectionData) {
		DefaultUiManager uiManager = new DefaultUiManager();
		return uiManager.getPage(page,connectionData.getUser());
	}
}
