/**
 * 
 */
package net.mysocio.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioUser;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.utils.rss.RssUtils;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 * 
 */
public class ImportOpmlHandler extends AbstractHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4875284442941433705L;
	private static final Logger logger = LoggerFactory
			.getLogger(ImportOpmlHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mysocio.ui.AbstractHandler#handleRequest(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws CommandExecutionException {
		IDataManager dataManager = DataManagerFactory.getDataManager();
		SocioUser user = null;
		String userId = (String) request.getSession().getAttribute("user");
		if (userId != null) {
			user = dataManager.getObject(SocioUser.class, userId);
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (user != null && isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();
			// Parse the request
			try {
				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					if (item.isFormField()) {
						logger.debug("Got form field while uploading OPML."
								+ item.getFieldName());
					} else {
						RssUtils.importOpml(user, item.openStream());
					}
				}
			} catch (Exception e) {
				logger.error("Error importing OPML file.", e);
				throw new CommandExecutionException(e);
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mysocio.ui.AbstractHandler#getLogger()
	 */
	@Override
	protected Logger getLogger() {
		return logger;
	}

}
