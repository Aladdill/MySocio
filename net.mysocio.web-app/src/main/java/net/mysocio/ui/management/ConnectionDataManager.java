/**
 * 
 */
package net.mysocio.ui.management;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManager;

/**
 * @author Aladdin
 *
 */
public class ConnectionDataManager {
	private static final Logger logger = LoggerFactory.getLogger(DataManager.class);
	public static SocioUser getUser(HttpServletRequest request){
		SocioUser user = (SocioUser)request.getSession().getAttribute("user");
		if (user == null){
			String identifier = request.getParameter("identifier");
			String identifierValue = request.getParameter(identifier);
			logger.debug("identifier="+identifier+" identifierValue="+identifierValue);
			user = DataManager.getUser(identifier, identifierValue);
			if (user == null){
				user = DataManager.createUser(identifier, identifierValue);
			}
			request.getSession().setAttribute("user",user);
		}
		return user;
	}
}
