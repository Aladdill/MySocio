/*
 * Copyright 2008 Troy Bourdon
 * 
 * This file is part of LJToolkit.
 *
 * LJToolkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LJToolkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LJToolkit.  If not, see <http://www.gnu.org/licenses/>.    
 */

package org.ljtoolkit.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a utility class used to read resource files for each package in
 * this toolkit library. The classes purpose is to aid in providing I18N
 * support.
 * 
 * @author Troy Bourdon
 *
 */
public class LJResourceBundle {

	// class attributes
	private static final String bundleName = ".resources";
	private static Map<String, LJResourceBundle> bundleMap = new HashMap<String, LJResourceBundle>();

	// instance attributes
	private final ResourceBundle bundle;
	private final String packageName;
	private Log logger = LogFactory.getLog(this.getClass());
	
	private LJResourceBundle(String _packageName) {
		packageName = _packageName;
		bundle = ResourceBundle.getBundle(packageName + bundleName);
	}
	
	public static LJResourceBundle getInstance(String _packageName) {
		if(bundleMap.get(_packageName) == null) {
			bundleMap.put(_packageName, new LJResourceBundle(_packageName));
		}
		
		return bundleMap.get(_packageName);
	}

    public static String getPackageName(Class<?> cls) {
        String clsNm = cls.getName();
        return clsNm.substring(0, clsNm.lastIndexOf("."));
    }

	public String getResource(String key) {
		try {
			String val = bundle.getString(key);
			if ("test".equals(System.getProperty("user.variant"))) {
				if (val.startsWith("/"))
					return val;
			
				return ">>> "+val+" <<<";
			}
			
			return val;
		} catch (MissingResourceException x) {
			String error = "Resource " + key + " was not found for resource: " 
				+ packageName + bundleName + " (active locale = " 
				+ System.getProperty("user.language") + "_"
				+ System.getProperty("user.country") + "_"
				+ System.getProperty("user.variant")+")";
			
			logger.error(error, x.getCause());
		}

		return "[<-- "+key+ " -->]";
	}
	
	public String getResource(String key, Object... args) {
		String fmt = getResource(key);
		return MessageFormat.format(fmt, args);
	}
	
	public int getIntResource(String key, int defaultValue) {
		int result = defaultValue;
		
		try	{
			result = Integer.parseInt(getResource(key));
		} catch(NumberFormatException e) {
			logger.error("An error ocurred getting the Integer resource, cause: " + e.getMessage());
		}
		return result;
	}		
}

