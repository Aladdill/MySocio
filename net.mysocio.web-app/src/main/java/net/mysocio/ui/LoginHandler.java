/**
 * 
 */
package net.mysocio.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mysocio.authentication.UserIdentifier;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.management.ConnectionData;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.managers.basic.DefaultResourcesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Aladdin
 *
 */
public class LoginHandler extends AbstractHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6174410260385459501L;
	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set a cookie for the user, so that the counter does not increate
		// everytime the user press refresh
		HttpSession session = request.getSession(true);
		// Set the session valid for 15 minutes
		session.setMaxInactiveInterval(15*60);

		response.setCharacterEncoding("UTF-8");
		IConnectionData connectionData = new ConnectionData(request);
		//TODO move initialization to spring 
		ServletContext servletContext = getServletContext();
		DefaultResourcesManager.init(servletContext.getRealPath(""));
		PrintWriter out = response.getWriter();
		String responseString = "";
		try {
			String identifierString = connectionData.getRequestParameter("identifier");
			if (identifierString != null){
				UserIdentifier identifier = UserIdentifier.valueOf(identifierString);
				if (identifier != null){
					logger.debug("Getting request url  for " + identifier.name());
					responseString = identifier.getAuthManager().getRequestUrl();
					request.getSession().setAttribute("identifier", identifier);
				}else{
					throw new  CommandExecutionException("No login medium was defined");
				}
			}
			String error = connectionData.getRequestParameter("error");
			if (error != null && error.equals("access_denied")){
				throw new CommandExecutionException("Are you sure you want to log in?");
			}
			String code = connectionData.getRequestParameter("code");
			if (code != null){
				UserIdentifier identifier = (UserIdentifier)request.getSession().getAttribute("identifier");
				try {
					identifier.getAuthManager().login(connectionData);
				} catch (Exception e) {
					throw new CommandExecutionException(e);
				}
				request.getSession().removeAttribute("identifier");
				responseString = DefaultResourcesManager.getPage("closingWindow.html");
			}
		} catch (CommandExecutionException e) {
			responseString = handleError(request, response, e, logger);
		}
		out.print(responseString);
	}
}
