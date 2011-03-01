/**
 * 
 */
package net.mysocio.data.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class ConnectionData implements IConnectionData{
	private static final Logger logger = LoggerFactory.getLogger(ConnectionData.class);
	private HttpSession session;
	private HttpServletRequest request;
	public ConnectionData(HttpServletRequest request){
		this.request = request;
		session = request.getSession();
	}
	@Override
	public SocioUser getUser(){
		SocioUser user = (SocioUser)session.getAttribute("user");
		if (user == null){
			String identifier = getRequestParameter("identifier");
			String identifierValue = getRequestParameter(identifier);
			logger.debug("identifier="+identifier+" identifierValue="+identifierValue);
			user = DataManagerFactory.getDataManager().getUser(identifier, identifierValue);
			if (user == null){
				user = DataManagerFactory.getDataManager().createUser(identifier, identifierValue);
			}
			session.setAttribute("user",user);
		}
		return user;
	}
	@Override
	public void cleanSession() {
		session.removeAttribute("user");
	}
	@Override
	public String getRequestParameter(String parameterName){
		return request.getParameter(parameterName);
	}
}
