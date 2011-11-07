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
 * @author Aladdin
 *
 */
public class DefaultCommandIterpreter implements ICommandInterpreter{
	private static final Logger logger = LoggerFactory.getLogger(DefaultCommandIterpreter.class);
	public static final String TEXT_XML = "text/xml";
	public static final String TEXT_HTML = "text/html";
	public static final String JSON = "json";
	private IConnectionData connectionData;
	
	public DefaultCommandIterpreter(IConnectionData connectionData) {
		super();
		this.connectionData = connectionData;
	}

	@Override
	public String executeCommand(String command) throws CommandExecutionException {
		logger.debug("Getting command.");
		EDefaultCommand commandObject = getCommandValue(command);
		logger.debug("executing command:" + command);
		String response = "No page set for this command";
		try {
			response = commandObject.getExecutor().execute(connectionData);
		} catch (Exception e) {
			logger.error("Command execution failed", e);
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
			logger.error("Unknown command");
			throw new CommandExecutionException(e);
		}
		return commandObject;
	}
	
	@Override
	public String getCommandResponseType(String command) throws CommandExecutionException{
		logger.debug("Getting response type.");
		EDefaultCommand commandObject = getCommandValue(command);
		String responseType = commandObject.getResponseType();
		logger.debug("Command response type:" + responseType);
		return responseType;
	}
}
