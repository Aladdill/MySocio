package net.mysocio.ui;

import javax.jdo.JDOHelper;
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
		IDataManager dataManager = DataManagerFactory.getDataManager();
		try {
			String command = request.getParameter("command");
			IConnectionData connectionData = new ConnectionData(request);
			String userId = (String) request.getSession().getAttribute("user");
			SocioUser user = null;
			if (userId != null) {
				user = (SocioUser)dataManager.getObject(SocioUser.class, userId);
				connectionData.setUser(user);
			}
			ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(connectionData);
			response.setContentType(commandInterpreter.getCommandResponseType(command));
			commandOutput = commandInterpreter.executeCommand(command);
			user = connectionData.getUser();
			if (user != null) {
				if (JDOHelper.isDirty(user)){
					DataManagerFactory.getDataManager(user).persistObject(user);
				}
				request.getSession().setAttribute("user", user.getId());
				if (logger.isDebugEnabled()) {
					logger.debug("User was inserted into session with name "
							+ user.getName());
				}
			}
		} catch (Exception e) {
			throw new CommandExecutionException(e);
		}
		return commandOutput;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
