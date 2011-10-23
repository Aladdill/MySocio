/**
 * 
 */
package net.mysocio.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.management.CommandExecutionException;

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
	
	/**
	 * @param request
	 * @param responseString
	 * @return
	 * @throws CommandExecutionException
	 */
	protected String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws CommandExecutionException {
		IConnectionData connectionData = new ConnectionData(request);
		String responseString = "";
		UserIdentifier identifier = UserIdentifier.test;
		Object identifierAttr = request.getSession().getAttribute("identifier");
		try {
			if (identifierAttr != null){
				identifier = (UserIdentifier)identifierAttr;
				connectionData.removeSessionAttribute("identifier");
			}else{
				String identifierString = connectionData.getRequestParameter("identifier");
				if (identifierString != null){
					identifier = UserIdentifier.valueOf(identifierString);
					if (identifier != null){
						request.getSession().setAttribute("identifier", identifier);
						logger.debug("Getting request url  for " + identifier.name());
					}else{
						throw new  CommandExecutionException("No login medium was defined");
					}
				}
			}
			responseString = identifier.getAuthManager().authenticate(connectionData);
		} catch (Exception e) {
			logger.error("Login error",e);
			throw new CommandExecutionException(e);
		}
		return responseString;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
