package net.mysocio.ui;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.management.ConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.CommandIterpreterFactory;
import net.mysocio.ui.management.ICommandInterpreter;
import net.mysocio.ui.managers.basic.DefaultResourcesManager;

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
	protected String handleRequest(HttpServletRequest request,
			HttpServletResponse response)
			throws CommandExecutionException {
		String command = request.getParameter("command");
		IConnectionData connectionData = new ConnectionData(request);
		//TODO move initialization to spring 
		ServletContext servletContext = getServletContext();
		DefaultResourcesManager.init(servletContext.getRealPath(""));
		ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(connectionData);
		response.setContentType(commandInterpreter.getCommandResponseType(command));
		return commandInterpreter.executeCommand(command);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
