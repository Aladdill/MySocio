package net.mysocio.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mysocio.ui.management.CommandIterpreterFactory;
import net.mysocio.ui.management.ConnectionDataManager;
import net.mysocio.ui.management.ICommandInterpreter;

/**
 * Servlet implementation class RequestHandler
 */
public class RequestHandler extends HttpServlet {
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
		// Set the session valid for 5 secs
		session.setMaxInactiveInterval(5);
		
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
		PrintWriter out = response.getWriter();
		ICommandInterpreter commandInterpreter = CommandIterpreterFactory.getCommandInterpreter(ConnectionDataManager.getUser(request));
		response.setContentType(commandInterpreter.getCommandResponseType(command));
		out.print(commandInterpreter.executeCommand(command, request.getParameterMap()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
