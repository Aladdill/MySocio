/**
 * 
 */
package net.mysocio.data.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import net.mysocio.data.SocioUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aladdin
 *
 */
public class DefaultResourcesManager{
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(DefaultResourcesManager.class);
	private static String servletContextPath;
	private static final String PROPERTIES_FOLDER = File.separator + "properties" + File.separator;
	private static Map<Locale, ResourceBundle> bundles;
	public static void init(String contextPath){
		if (servletContextPath == null){
			servletContextPath = contextPath;
		}
		if (bundles == null){
			bundles = new HashMap<Locale, ResourceBundle>();
			createBundle(Locale.ENGLISH);
		}
	}
	/**
	 * @param locale 
	 * @return
	 */
	private static PropertyResourceBundle createBundle(Locale locale){
		PropertyResourceBundle propertyResourceBundle = createDefaultBundle();
		try {
			String fileName = servletContextPath + PROPERTIES_FOLDER + "textResources_" + locale.getLanguage() + ".properties";
			propertyResourceBundle = createResourceBundle(fileName);
		} catch (Exception e) {
			logger.error("Coudn't get bundle for locale " + locale.getLanguage());
		}
		bundles.put(locale, propertyResourceBundle);
		return propertyResourceBundle;
	}
	
	private static PropertyResourceBundle createDefaultBundle(){
		PropertyResourceBundle propertyResourceBundle = null;
		try {
			String fileName = servletContextPath + PROPERTIES_FOLDER + "textResources.properties";
			propertyResourceBundle = createResourceBundle(fileName);
		} catch (Exception e) {
			logger.error("Coudn't get bundle for default locale.");
		}
		return propertyResourceBundle;
	}
	
	private static PropertyResourceBundle createResourceBundle(String fileName)
			throws IOException, FileNotFoundException {
		return new PropertyResourceBundle(new InputStreamReader(new FileInputStream(fileName), Charset.forName(DEFAULT_ENCODING)));
	}
	
	public static String getResource(Locale locale, String resource){
		ResourceBundle resources = bundles.get(locale);
		if (resources == null){
			resources = createBundle(locale);
		}
		String resourceValue;
		try {
			resourceValue = resources.getString(resource);
		} catch (MissingResourceException e) {
			resourceValue = resource;
//			logger.debug("Resource missing for key: " + resource);
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
		String filepath = servletContextPath + File.separator + "pages" + File.separator + pageName;
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
		d.close();
		return out.toString();
	}
	public static boolean isMailInList(String userMail) {
		Properties mails = new Properties();
		try {
			String filepath = servletContextPath + File.separator + "resources" + File.separator + "mails.list.properties";
			mails.load(new FileInputStream(filepath));
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
