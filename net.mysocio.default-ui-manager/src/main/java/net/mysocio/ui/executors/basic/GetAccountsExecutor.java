/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.util.List;
import java.util.Locale;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.UserAccount;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.data.objects.AccountLine;
import net.mysocio.ui.data.objects.DefaultAccountLine;
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
public class GetAccountsExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(GetAccountsExecutor.class);
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		String page = "";
		try {
			String userId = connectionData.getUserId();
			IDataManager dataManager = DataManagerFactory.getDataManager();
			SocioUser user = dataManager.getObject(SocioUser.class, userId);
			List<UserAccount> accounts = dataManager.getAccounts(userId);
			AbstractUiManager uiManager = new DefaultUiManager();
			Locale locale = new Locale(user.getLocale());
			String accountHTML = uiManager.getPage(AccountLine.CATEGORY, AccountLine.NAME, userId);
			for (UserAccount userAccount : accounts) {
				String currentAccountHTML = accountHTML;
				if (userAccount.getAccount().getId().equals(user.getMainAccount().getId())){
					currentAccountHTML = uiManager.getPage(DefaultAccountLine.CATEGORY, DefaultAccountLine.NAME,userId);
				}
				currentAccountHTML = currentAccountHTML.replace("account.icon", DefaultResourcesManager.getResource(locale, (userAccount.getAccount().getIconUrl())));
				currentAccountHTML = currentAccountHTML.replace("account.id", userAccount.getAccount().getId().toString());
				currentAccountHTML = currentAccountHTML.replace("account.userpic", userAccount.getAccount().getUserpicUrl());
				currentAccountHTML = currentAccountHTML.replace("account.username", userAccount.getAccount().getUserName());
				page += currentAccountHTML;
			}
		} catch (CorruptedDataException e) {
			logger.error("Failed showing accounts",e);
			throw new CommandExecutionException(e);
		}
		return page;
	}
}
