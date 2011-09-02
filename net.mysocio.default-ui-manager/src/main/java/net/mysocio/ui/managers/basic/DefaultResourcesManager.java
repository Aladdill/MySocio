/**
 * 
 */
package net.mysocio.ui.managers.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import net.mysocio.data.SocioUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class DefaultResourcesManager {
	private static final Logger logger = LoggerFactory.getLogger(DefaultResourcesManager.class);
	private static String servletContextPath;
	private static ResourceBundle resources;
	public static void init(String contextPath){
		if (servletContextPath == null){
			servletContextPath = contextPath;
		}
		if (resources == null){
			resources = ResourceBundle.getBundle("textResources", Locale.ENGLISH);
		}
	}
	public static String getResource(Locale locale, String resource){
		String resourceValue;
		try {
			resourceValue = resources.getString(resource);
		} catch (MissingResourceException e) {
			resourceValue = resource;
			logger.warn("Resource missing.", e);
		}
		return resourceValue;
	}
	public static String getResource(Locale locale, String resource, String[] insertions){
		String resourceValue = getResource(locale, resource);
		for (int i = 0; i < insertions.length; i++) {
			resourceValue = resourceValue.replace("{" + i + "}", insertions[i]);
		}
		return resourceValue;
	}
	public static String getResource(SocioUser user, String resource){
		return getResource(new Locale(user.getLocale()), resource);
	}
	public static String getPage(String pageName){
		String filepath = servletContextPath + "/pages/" + pageName;
		String fileContent = "";
		try {
			fileContent = readFile(filepath);
		} catch (IOException e) {
			logger.error("Can't read page.", e);
		}
		return fileContent;
	}
	private static String readFile(String path) throws IOException{
		StringBuffer out = new StringBuffer();
		BufferedReader d = new BufferedReader(new FileReader(new File(path)));
		for (String line = d.readLine(); line != null; line = d.readLine()){
			out.append(line);
        }
		return out.toString();
	}
	public static boolean isMailInList(String userMail) {
		Properties mails = new Properties();
		try {
			String filepath = servletContextPath + "/resources/mails.list.properties";
			mails.load(new FileReader(new File(filepath)));
			String[] mailsList = ((String)mails.get("mails")).split(",");
			for (String mail : mailsList) {
				if (mail.equals(userMail)){
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Resources file is missing.", e);
		}
		return false;
	}
}
