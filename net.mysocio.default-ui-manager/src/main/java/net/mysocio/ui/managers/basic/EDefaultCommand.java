package net.mysocio.ui.managers.basic;

import net.mysocio.ui.data.objects.DefaultSiteBody;
import net.mysocio.ui.data.objects.RssConnections;
import net.mysocio.ui.executors.basic.AddRssFeedExecutor;
import net.mysocio.ui.executors.basic.GetMessagesExecutor;
import net.mysocio.ui.executors.basic.GetSourcesExecutor;
import net.mysocio.ui.executors.basic.LoadPageExecutor;
import net.mysocio.ui.executors.basic.LoginPageExecutor;
import net.mysocio.ui.management.ICommandExecutor;


public enum EDefaultCommand {
	getMessages(new GetMessagesExecutor()),
	openStartPage(new LoginPageExecutor()),
	logout(new LoginPageExecutor()),
	openMainPage(new LoadPageExecutor(new DefaultSiteBody())),
	openSettings(new LoadPageExecutor(new RssConnections())),
	addRssFeed(new AddRssFeedExecutor()),
	getRssFeeds(new GetRssFeedsExecutor()),
	getSources(new GetSourcesExecutor(), DefaultCommandIterpreter.TEXT_XML);
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
