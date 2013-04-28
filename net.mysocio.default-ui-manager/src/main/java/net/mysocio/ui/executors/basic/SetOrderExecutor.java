/**
 * 
 */
package net.mysocio.ui.executors.basic;

import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioTag;
import net.mysocio.data.UserTags;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.management.ICommandExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class SetOrderExecutor implements ICommandExecutor {
	private static final Logger logger = LoggerFactory.getLogger(SetOrderExecutor.class);
	/* (non-Javadoc)
	 * @see net.mysocio.ui.management.ICommandExecutor#execute(net.mysocio.data.IConnectionData)
	 */
	public String execute(IConnectionData connectionData) throws CommandExecutionException{
		String order = connectionData.getRequestParameter("order");
		try {
			if (order != null && (order.equals(SocioTag.ASCENDING_ORDER) || order.equals(SocioTag.DESCENDING_ORDER))){
				UserTags userTags = connectionData.getUserTags();
				SocioTag tag = userTags.getTag(userTags.getSelectedTag());
				tag.setOrder(order);
				DataManagerFactory.getDataManager().saveObject(userTags);
			}
		} catch (Exception e) {
			logger.error("Error occured while changing tag order.");
			throw new CommandExecutionException(e);
		}
		return new GetRssFeedsExecutor().execute(connectionData);
	}
}
