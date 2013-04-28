package net.mysocio.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.CommandIterpreterFactory;
import net.mysocio.ui.management.ICommandInterpreter;
import net.mysocio.ui.managers.basic.EDefaultCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class RequestHandler
 */
public class RequestHandler extends AbstractHandler {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @param request
	 * @param response
	 * @param command
	 * @throws CommandExecutionException
	 */
	protected String handleRequest(HttpServletRequest request,HttpServletResponse response) throws CommandExecutionException {
		String commandOutput;
		String command = request.getParameter("command");
		IConnectionData connectionData = new ConnectionData(request);
		synchronized(connectionData){
			String pendingCommand = connectionData.getSessionAttribute(command);
			int counter = 0;
			if (pendingCommand != null){
				counter = Integer.parseInt(pendingCommand);
			}
			counter ++;
			connectionData.setSessionAttribute(command, Integer.toString(counter));
		}
		String userId = null;
		try {
			userId = (String) request.getSession().getAttribute("user");
			if (userId != null) {
				connectionData.setUserId(userId);
				UserTags userTags = DataManagerFactory.getDataManager().getUserTags(userId);
				connectionData.setUserTags(userTags);
				connectionData.setSelectedTag(userTags.getSelectedTag());
			}else{
				if (!command.equals(EDefaultCommand.startAuthentication.name()) && 
						!command.equals(EDefaultCommand.login.name()) &&
						!command.equals(EDefaultCommand.authenticationDone.name()) &&
						!command.equals(EDefaultCommand.openStartPage.name())){
					throw new CommandExecutionException("restart");
				}
			}
			ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(connectionData);
			response.setContentType(commandInterpreter.getCommandResponseType(command));
			commandOutput = commandInterpreter.executeCommand(command);
			userId = connectionData.getUserId();
			if (userId != null) {
				request.getSession().setAttribute("user", userId);
				if (logger.isDebugEnabled()) {
					logger.debug("User was inserted into session with id "
							+ userId);
				}
			}
		} catch (CommandExecutionException e) {
			if (userId != null) {
				request.getSession().setAttribute("user", userId);
				if (logger.isDebugEnabled()) {
					logger.debug("User was inserted into session with id "
							+ userId);
				}
			}
			throw e;
		}finally{
			synchronized(connectionData){
				String pendingCommand = connectionData.getSessionAttribute(command);
				int counter = 0;
				if (pendingCommand != null){
					counter = Integer.parseInt(pendingCommand);
					counter --;
					if (counter != 0){
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
					connectionData.setSessionAttribute(command, Integer.toString(counter));
				}else{
					logger.error("Command finished without starting!");
				}
			}
		}
		return commandOutput;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
