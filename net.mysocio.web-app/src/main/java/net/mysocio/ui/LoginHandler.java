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
		UserIdentifier identifier;
		Object identifierAttr = request.getSession().getAttribute("identifier");
		try {
			if (identifierAttr != null){
				identifier = (UserIdentifier)identifierAttr;
				responseString = identifier.getAuthManager().authenticate(connectionData);
			}else{
				String identifierString = connectionData.getRequestParameter("identifier");
				if (identifierString != null){
					identifier = UserIdentifier.valueOf(identifierString);
					if (identifier != null){
						logger.debug("Getting request url  for " + identifier.name());
						responseString = identifier.getAuthManager().authenticate(connectionData);
						request.getSession().setAttribute("identifier", identifier);
					}else{
						throw new  CommandExecutionException("No login medium was defined");
					}
				}
			}
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
