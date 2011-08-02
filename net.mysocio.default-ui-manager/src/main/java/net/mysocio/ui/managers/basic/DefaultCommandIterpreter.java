/**
 * 
 */
package net.mysocio.ui.managers.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
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
	public String executeCommand(String command) throws CommandExecutionException {
		logger.debug("executing command:" + command);
		EDefaultCommand commandObject = getCommandValue(command);
		String response = "No page set for this command";
		try {
			response = commandObject.getExecutor().execute(connectionData);
		} catch (Exception e) {
			throw new CommandExecutionException(e);
		}
		return response;
	}

	/**
	 * @param command
	 * @return
	 * @throws CommandExecutionException 
	 */
	private EDefaultCommand getCommandValue(String command) throws CommandExecutionException {
		logger.debug("parsing command:" + command);
		EDefaultCommand commandObject = null;
		if (command == null){
			throw new CommandExecutionException("Received null command.");
		}
		try {
			commandObject = EDefaultCommand.valueOf(command);
		} catch (IllegalArgumentException e) {
			throw new CommandExecutionException(e);
		}
		return commandObject;
	}
	
	@Override
	public String getCommandResponseType(String command) throws CommandExecutionException{
		EDefaultCommand commandObject = getCommandValue(command);
		String responseType = commandObject.getResponseType();
		logger.debug("Command response type:" + responseType);
		return responseType;
	}
}
