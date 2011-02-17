/**
 * 
 */
package net.socio.ui.managers.basic;

import java.util.Map;

import net.mysocio.data.SocioUser;
import net.mysocio.ui.management.ICommandInterpreter;

/**
 * @author gurfinke
 *
 */
public class DefaultCommandIterpreter implements ICommandInterpreter{
	public static final String TEXT_XML = "text/xml";
	public static final String TEXT_HTML = "text/html";
	private DefaultUiManager uiManager = new DefaultUiManager();
	private SocioUser user;
	
	public DefaultCommandIterpreter(SocioUser user) {
		super();
		this.user = user;
	}

	@Override
	public String executeCommand(String command, Map parameters) {
		if (command == null){
			return uiManager.getStartingPage();
		}
		EDefaultCommand commandObject = EDefaultCommand.valueOf(command);
		String response = "No page set for this command";
		response = commandObject.getExecutor().execute(user, parameters);
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
