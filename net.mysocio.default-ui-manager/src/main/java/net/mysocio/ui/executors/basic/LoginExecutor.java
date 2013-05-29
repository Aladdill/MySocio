/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.UserTags;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.google.GoogleAccount;
import net.mysocio.data.management.AccountsManager;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;
import net.mysocio.ui.management.UnapprovedUserException;
import net.mysocio.utils.rss.RssUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
public class LoginExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(LoginExecutor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.
	 * IConnectionData)
	 */
	@Override
	public String execute(IConnectionData connectionData)
			throws CommandExecutionException {
		try {
			String userId = connectionData.getUserId();
			if (userId == null) {
				logger.debug("Logging in user.");
				IDataManager dataManager = DataManagerFactory.getDataManager();
				Account account = AccountsManager.getInstance().getAccount(connectionData);
				SocioUser user = account.getUser();
				if (user != null) {
					logger.debug("Account found");
				}else{
					user = dataManager.createUser(account,connectionData.getLocale());
					if (account instanceof GoogleAccount){
						String data = ((GoogleAccount)account).getGoogleReaderFeeds(userId);
						if (data != null){
							RssUtils.importGoogleReaderFeeds(userId, data);
						}
					}
				}
				userId = user.getId().toString();
				UserTags userTags = dataManager.getUserTags(userId);
				connectionData.setUserTags(userTags);
				connectionData.setUserId(userId);
			}else{
				logger.debug("User already logged in.");
			}
		}catch (Exception e) {
			logger.error("Failed to create account.", e);
			if (e instanceof UnapprovedUserException){
				throw new CommandExecutionException(e.getMessage());
			}
			throw new CommandExecutionException(e);
		}
		connectionData.removeSessionAttribute(AccountsManager.IDENTIFIER);
		connectionData.removeSessionAttribute("code");
		return "";
	}

}
