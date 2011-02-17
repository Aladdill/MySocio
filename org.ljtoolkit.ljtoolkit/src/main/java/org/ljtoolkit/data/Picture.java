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

package org.ljtoolkit.data;

import java.net.URL;

/**
 * This class represents a Live Journal picture.
 * 
 * @author Troy Bourdon
 *
 */
public class Picture {

	private String keyword;
	private URL url;
	
	/**
	 * Get the picture keyword.
	 * 
	 * @return String
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * Set the picture keyword.
	 * 
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	/**
	 * Get the picture url.
	 * 
	 * @return URL
	 */
	public URL getUrl() {
		return url;
	}
	
	/**
	 * Set the picture url.
	 * 
	 * @param url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
}
