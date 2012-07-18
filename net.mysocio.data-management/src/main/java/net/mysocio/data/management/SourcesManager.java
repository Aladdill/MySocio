/**
 * 
 */
package net.mysocio.data.management;

import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.data.IDataManager;
import net.mysocio.data.SocioTag;
import net.mysocio.data.SocioUser;

/**
 * @author Aladdin
 *
 */
public class SourcesManager {
	public static void addSourceToUser(SocioUser user, Source source) throws Exception{
		IDataManager dataManager = DataManagerFactory.getDataManager();
		Source savedSource = (Source)dataManager.createSource(source);
		source.createRoute("activemq:" + user.getId()  + ".newMessage");
		List<SocioTag> tags = savedSource.getTags();
		for (SocioTag tag : tags) {
			tag.setUserId(user.getId().toString());
		}
		dataManager.saveObjects(tags);
		user.addSource(savedSource);
		dataManager.saveObject(user);
	}
}
