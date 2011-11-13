package net.mysocio.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.CommandIterpreterFactory;
import net.mysocio.ui.management.ICommandInterpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class RequestHandler
 */
public class RequestHandler extends AbstractHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(RequestHandler.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @param request
	 * @param response
	 * @param command
	 * @throws CommandExecutionException
	 */
	protected String handleRequest(HttpServletRequest request,HttpServletResponse response) throws CommandExecutionException {
		String command = request.getParameter("command");
		IConnectionData connectionData = new ConnectionData(request);
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioUser user = (SocioUser) request.getSession().getAttribute("user");
		if (user != null) {
			connectionData.setUser(dataManager.persistObject(user));
		}
		ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(connectionData);
		response.setContentType(commandInterpreter.getCommandResponseType(command));
		String commandOutput = commandInterpreter.executeCommand(command);
		user = connectionData.getUser();
		if (user != null) {
			dataManager.saveObject(user);
			request.getSession().setAttribute("user", dataManager.detachObject(user));
			if (logger.isDebugEnabled()) {
				logger.debug("User was inserted into session with name "
						+ user.getName());
			}
			// I want to flush changes in user data every time request finished.
			DataManagerFactory.getDataManager().flush();
		}
		return commandOutput;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
