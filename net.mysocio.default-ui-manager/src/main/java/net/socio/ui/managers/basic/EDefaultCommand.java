package net.socio.ui.managers.basic;

import net.mysocio.ui.management.ICommandExecutor;
import net.socio.ui.executors.basic.GetMessagesExecutor;
import net.socio.ui.executors.basic.GetSourcesExecutor;


public enum EDefaultCommand {
	getMessages(new GetMessagesExecutor()), 
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
