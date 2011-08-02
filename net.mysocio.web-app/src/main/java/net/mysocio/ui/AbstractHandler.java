/**
 * 
 */
package net.mysocio.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.ui.management.CommandExecutionException;

import org.slf4j.Logger;

/**
 * @author Aladdin
 *
 */
public abstract class AbstractHandler extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6643496918578749898L;
	
	/**
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 * @throws IOException
	 */
	protected String handleError(HttpServletRequest request,
			HttpServletResponse response, CommandExecutionException e, Logger logger)
			throws IOException {
		String responseString;
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		responseString = e.getMessage();
		if (logger.isErrorEnabled()){
			logger.error("Request content type: " + request.getContentType() + "\nRequest content:");
			while (request.getReader().ready()){
				logger.error(request.getReader().readLine());
			}
		}
		return responseString;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
