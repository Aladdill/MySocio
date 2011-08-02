package net.mysocio.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set a cookie for the user, so that the counter does not increate
		// everytime the user press refresh
		HttpSession session = request.getSession(true);
		String command = request.getParameter("command");
		// Set the session valid for 15 minutes
		session.setMaxInactiveInterval(15*60);
		
		response.setCharacterEncoding("UTF-8");
		IConnectionData connectionData = new ConnectionData(request);
		//TODO move initialization to spring 
		ServletContext servletContext = getServletContext();
		DefaultResourcesManager.init(servletContext.getRealPath(""));
		PrintWriter out = response.getWriter();
		ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(connectionData);
		String responseString = "";
		try {
			response.setContentType(commandInterpreter.getCommandResponseType(command));
			responseString = commandInterpreter.executeCommand(command);
		} catch (CommandExecutionException e) {
			responseString = handleError(request, response, e, logger);
		}
		out.print(responseString);
	}
}
