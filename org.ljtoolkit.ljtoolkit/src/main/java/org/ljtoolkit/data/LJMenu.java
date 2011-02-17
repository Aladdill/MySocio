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
 * Class that represents Live Journal menus.
 * 
 * @author Troy Bourdon
 *
 */
public class LJMenu {
	private String name;
	private URL url;
	private LJMenu subMenu;
	
	/**
	 * The text of the itemnumth menu item (1-based index) in the menunumth menu
	 * (0-based index.. kinda. it's not really an array.) If the text is a single hyphen "-",
	 * then show a menu separator bar instead of any text.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the menu text.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * The URL to jump to for the itemnumth menu item (1-based index) in the menunumth menu. 
	 * This may be absent, in which case this is a menu item which opens a sub-menu.
	 * 
	 * @return URL
	 */
	public URL getUrl() {
		return url;
	}
	
	/**
	 * Set the URL to jump to for the itemnumth menu item.
	 * @param url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
	/**
	 * For menus that don't have associated URLs, this key contains the menunum of the associated
	 * sub menu. Call your menu creation function recursively and start making that menu.
	 * 
	 * @return LJMenu
	 */
	public LJMenu getSubMenu() {
		return subMenu;
	}
	
	/**
	 * Set a associated sub menu.
	 * 
	 * @param subMenu
	 */
	public void setSubMenu(LJMenu subMenu) {
		this.subMenu = subMenu;
	}
}
