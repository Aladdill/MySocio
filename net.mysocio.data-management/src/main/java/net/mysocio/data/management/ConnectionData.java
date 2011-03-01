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
		return (SocioUser)session.getAttribute("user");
	}
	@Override
	public void cleanSession() {
		session.removeAttribute("user");
	}
	@Override
	public String getRequestParameter(String parameterName){
		return request.getParameter(parameterName);
	}
	@Override
	public void setUser(SocioUser user) {
		session.setAttribute("user", user);
	}
}
