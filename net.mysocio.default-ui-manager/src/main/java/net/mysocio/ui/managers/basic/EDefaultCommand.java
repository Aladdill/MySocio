package net.mysocio.ui.managers.basic;

import net.mysocio.ui.data.objects.DefaultSiteBody;
import net.mysocio.ui.data.objects.RssConnections;
import net.mysocio.ui.executors.basic.CreateAccountExecutor;
import net.mysocio.ui.executors.basic.AddRssFeedExecutor;
import net.mysocio.ui.executors.basic.GetAccountsExecutor;
import net.mysocio.ui.executors.basic.GetMessagesExecutor;
import net.mysocio.ui.executors.basic.GetRssFeedsExecutor;
import net.mysocio.ui.executors.basic.GetSourcesExecutor;
import net.mysocio.ui.executors.basic.LoadPageExecutor;
import net.mysocio.ui.executors.basic.LoginPageExecutor;
import net.mysocio.ui.executors.basic.LogoutPageExecutor;
import net.mysocio.ui.executors.basic.MarkMessagesReadenExecutor;
import net.mysocio.ui.executors.basic.RemoveAccountExecutor;
import net.mysocio.ui.executors.basic.RemoveRssFeedExecutor;
import net.mysocio.ui.management.ICommandExecutor;


public enum EDefaultCommand {
	getMessages(new GetMessagesExecutor()),
	openStartPage(new LoginPageExecutor()),
	logout(new LogoutPageExecutor()),
	openMainPage(new LoadPageExecutor(new DefaultSiteBody())),
	getContacts(new LoadPageExecutor(new RssConnections())),
	getRssFeeds(new GetRssFeedsExecutor()),
	getAccounts(new GetAccountsExecutor()),
	addRssFeed(new AddRssFeedExecutor()),
	removeRssFeed(new RemoveRssFeedExecutor()),
	addAccount(new CreateAccountExecutor()),
	removeAccount(new RemoveAccountExecutor()),
	getSources(new GetSourcesExecutor(), DefaultCommandIterpreter.JSON),
	markMessagesReaden(new MarkMessagesReadenExecutor(), DefaultCommandIterpreter.JSON);
	private ICommandExecutor executor;
	private String responseType = DefaultCommandIterpreter.TEXT_HTML;
	private EDefaultCommand(ICommandExecutor executor){
		this.executor = executor;
	}
	
	private EDefaultCommand(ICommandExecutor executor, String responseType){
		this.executor = executor;
		this.responseType = responseType;
	}
	public ICommandExecutor getExecutor(){
		return executor;
	}
	/**
	 * @return the responseType
	 */
	public String getResponseType() {
		return responseType;
	}
}
