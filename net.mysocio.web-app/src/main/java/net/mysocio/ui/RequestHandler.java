package net.mysocio.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.ConnectionData;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.CommandIterpreterFactory;
import net.mysocio.ui.management.ICommandInterpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class RequestHandler
 */
public class RequestHandler extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final long serialVersionUID = 1L;
	private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
	private static final String SCOPE = "https://docs.google.com/feeds/"; 
       
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
		
//		response.setCharacterEncoding("UTF-8");
//		OAuthService service = new ServiceBuilder()
//        .provider(GoogleApi.class)
//        .apiKey("anonymous")
//        .apiSecret("anonymous")
//        .scope(SCOPE)
//        .build();
//		Token requestToken = service.getRequestToken();
//		System.out.println(AUTHORIZE_URL + requestToken.getToken());
//		response.sendRedirect(AUTHORIZE_URL + requestToken.getToken());
		IConnectionData connectionData = new ConnectionData(request);
		initUser(connectionData);
		PrintWriter out = response.getWriter();
		ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(connectionData);
		response.setContentType(commandInterpreter.getCommandResponseType(command));
		String responseString = "";
		try {
			responseString = commandInterpreter.executeCommand(command);
		} catch (CommandExecutionException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseString = e.getMessage();
		}
		out.print(responseString);
	}

	private void initUser(IConnectionData connectionData) {
		SocioUser user = connectionData.getUser();
		if (user == null){
			String identifier = connectionData.getRequestParameter("identifier");
			if (identifier != null){
				String identifierValue = connectionData.getRequestParameter(identifier);
				logger.debug("identifier="+identifier+" identifierValue="+identifierValue);
				user = DataManagerFactory.getDataManager().getUser(identifier, identifierValue);
				if (user == null){
					user = DataManagerFactory.getDataManager().createUser(identifier, identifierValue, connectionData.getLocale());
				}
				connectionData.setUser(user);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
