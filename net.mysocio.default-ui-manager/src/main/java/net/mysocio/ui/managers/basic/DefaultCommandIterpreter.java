/**
 * 
 */
package net.mysocio.ui.managers.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.executors.basic.LoginPageExecutor;
import net.mysocio.ui.management.ICommandInterpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gurfinke
 *
 */
public class DefaultCommandIterpreter implements ICommandInterpreter{
	private static final Logger logger = LoggerFactory.getLogger(DefaultCommandIterpreter.class);
	public static final String TEXT_XML = "text/xml";
	public static final String TEXT_HTML = "text/html";
	private IConnectionData connectionData;
	
	public DefaultCommandIterpreter(IConnectionData connectionData) {
		super();
		this.connectionData = connectionData;
	}

	@Override
	public String executeCommand(String command) {
		logger.debug("executing command:" + command);
		if (command == null){
			return new LoginPageExecutor().execute(connectionData);
		}
		EDefaultCommand commandObject = EDefaultCommand.valueOf(command);
		String response = "No page set for this command";
		response = commandObject.getExecutor().execute(connectionData);
		return response;
	}
	
	@Override
	public String getCommandResponseType(String command){
		if (command == null){
			logger.debug("Command response type:" + TEXT_HTML);
			return TEXT_HTML;
		}
		EDefaultCommand commandObject = EDefaultCommand.valueOf(command);
		String responseType = commandObject.getResponseType();
		logger.debug("Command response type:" + responseType);
		return responseType;
	}
}
