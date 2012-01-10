/**
 * 
 */
package net.mysocio.ui.executors.basic;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.jdo.annotations.PersistenceAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mysocio.data.CorruptedDataException;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DefaultResourcesManager;
import net.mysocio.ui.data.objects.AccountLine;
import net.mysocio.ui.data.objects.DefaultAccountLine;
import net.mysocio.ui.data.objects.NewAccountLine;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.managers.basic.AbstractUiManager;
import net.mysocio.ui.managers.basic.DefaultUiManager;

/**
 * @author Aladdin
 *
 */
@PersistenceAware
public class GetAccountsExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(GetAccountsExecutor.class);
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		String page = "";
		try {
			SocioUser user = connectionData.getUser();
			List<Account> accounts = user.getAccounts();
			AbstractUiManager uiManager = new DefaultUiManager();
			Set<String> accountsTypes = AccountsManager.getInstance().getAccounts();
			String newAccountHTML = uiManager.getPage(NewAccountLine.CATEGORY, NewAccountLine.NAME, user);
			Locale locale = new Locale(user.getLocale());
			for (String identifier : accountsTypes) {
				String currentAccountHTML = newAccountHTML;
				currentAccountHTML = currentAccountHTML.replace("account.icon", DefaultResourcesManager.getResource(locale, (identifier + ".icon.account")));
				currentAccountHTML = currentAccountHTML.replace("account.add.new", DefaultResourcesManager.getResource(locale, ("account.add.new"), new String[]{DefaultResourcesManager.getResource(locale,identifier + ".tag")}));
				currentAccountHTML = currentAccountHTML.replace("account.identifier", identifier);
				page += currentAccountHTML;
			}
			String accountHTML = uiManager.getPage(AccountLine.CATEGORY, AccountLine.NAME, user);
			for (Account account : accounts) {
				String currentAccountHTML = accountHTML;
				if (account.getId().equals(user.getMainAccount().getId())){
					currentAccountHTML = uiManager.getPage(DefaultAccountLine.CATEGORY, DefaultAccountLine.NAME,user);
				}
				currentAccountHTML = currentAccountHTML.replace("account.icon", DefaultResourcesManager.getResource(locale, (account.getIconUrl())));
				currentAccountHTML = currentAccountHTML.replace("account.id", account.getId());
				currentAccountHTML = currentAccountHTML.replace("account.userpic", account.getUserpicUrl());
				currentAccountHTML = currentAccountHTML.replace("account.username", account.getUserName());
				page += currentAccountHTML;
			}
		} catch (CorruptedDataException e) {
			logger.error("Failed showing accounts",e);
			throw new CommandExecutionException(e);
		}
		return page;
	}
}
