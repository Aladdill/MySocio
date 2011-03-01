/**
 * 
 */
package net.socio.ui.managers.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.ICommandInterpreter;

/**
 * @author gurfinke
 *
 */
public class DefaultCommandIterpreter implements ICommandInterpreter{
	public static final String TEXT_XML = "text/xml";
	public static final String TEXT_HTML = "text/html";
	private DefaultUiManager uiManager = new DefaultUiManager();
	private IConnectionData connectionData;
	
	public DefaultCommandIterpreter(IConnectionData connectionData) {
		super();
		this.connectionData = connectionData;
	}

	@Override
	public String executeCommand(String command) {
		if (command == null){
			return uiManager.getStartingPage();
		}
		EDefaultCommand commandObject = EDefaultCommand.valueOf(command);
		String response = "No page set for this command";
		response = commandObject.getExecutor().execute(connectionData);
		return response;
	}
	
	@Override
	public String getCommandResponseType(String command){
		if (command == null){
			return TEXT_HTML;
		}
		EDefaultCommand commandObject = EDefaultCommand.valueOf(command);
		String responseType = commandObject.getResponseType();
		return responseType;
	}
}
