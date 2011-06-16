/**
 * 
 */
package net.mysocio.data.management;

import java.io.File;
import java.io.FileReader;
import java.util.Locale;
import java.util.Properties;

import net.mysocio.data.SocioUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class DefaultResourcesManager {
	private static final Logger logger = LoggerFactory.getLogger(DefaultResourcesManager.class);
	public static String getResource(Locale locale, String resource){
		Properties resources = new Properties();
		try {
			resources.load(new FileReader(new File("/resources/default.resources")));
		} catch (Exception e) {
			logger.error("Resources file is missing.", e);
		}
		return resources.getProperty(resource);
	}
	public static String getResource(SocioUser user, String resource){
		return getResource(new Locale(user.getLocale()), resource);
	}
}
