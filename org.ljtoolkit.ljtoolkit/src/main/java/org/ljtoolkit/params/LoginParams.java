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

package org.ljtoolkit.params;

/**
 * This class represents the parameters used with the Login service.
 * 
 * @author Troy Bourdon
 *
 */
public class LoginParams extends BaseParams {
	private String clientVersion;
	private Integer moods;
	private boolean menus;
	private boolean pictureKeywords;
	private boolean pictureKeywordUrls;
	
	/**
	 * (Optional) Although optional, this should be a string of the form Platform-ProductName/ClientVersionMajor.Minor.Rev,
	 * like Win32-MFC/1.2.7 or Gtk-LoserJabber/1.0.4. Note in this case that "Gtk" is not a platform, but rather a toolkit, 
	 * since the toolkit is multi-platform (Linux, FreeBSD, Solaris, Windows...).You make the judge what is best to send,
	 * but if it's of this form, we'll give you cool statistics about your users.
	 * 
	 * @return String
	 */	
	public String getClientVersion() {
		return clientVersion;
	}

	/**
	 * Returns the client identification(version) string.
	 * 
	 * @param version The client version
	 */
	public void setClientVersion(String version) {
		this.clientVersion = version;
	}
	
	/**
	 * Returns the user moods
	 * @return moods
	 */
	public Integer getMoods() {
		return moods;
	}
	
	/**
	 * If your client supports moods, send this key with a value of the highest mood ID you have cached/stored
	 * on the user's computer. For example, if you logged in last time with and got mood IDs 1, 2, 4, and 5,
	 * then send "5" as the value of "getmoods". The server will return every new mood that has an internal
	 * MoodID greater than 5. If you've never downloaded moods before, send "0".
	 * If you don't care about getting any moods at all (if your client doesn't support them),
	 * then don't send this key at all.
	 * 
	 * @param moods
	 */
	public void setMoods(Integer moods) {
		this.moods = moods;
	}
	
	/**
	 * Returns the user menus
	 * @return menus
	 */
	public boolean getMenus() {
		return menus;
	}
	
	/**
	 * Send something for this key if you want to get a list/tree of web jump menus to show in your client.
	 * @param menus
	 */
	public void setMenus(boolean menus) {
		this.menus = menus;
	}
	
	/**
	 * Returns the picture keywords flag
	 * @return pictureKeywords
	 */
	public boolean getPictureKeywords() {
		return pictureKeywords;
	}
	
	/**
	 * If your client supports picture keywords and you want to receive that list, send something for this key,
	 * like "1", and you'll receieve the list of picture keywords the user has defined.
	 * 
	 * @param pictureKeywords
	 */
	public void setPictureKeywords(boolean pictureKeywords) {
		this.pictureKeywords = pictureKeywords;
	}
	
	/**
	 * Returns the picture keyword urls
	 * @return pictureKeywordUrls
	 */
	public boolean getPictureKeywordUrls() {
		return pictureKeywordUrls;
	}
	
	/**
	 * If your client supports picture keywords and can also display the pictures somehow, send something for this key,
	 * like "1", and you'll receieve the list of picture keyword URLs that correspond to the picture keywords as well
	 * as the URL for the default picture. You must send getpickws for this option to even matter.
	 * 
	 * @param pictureKeywordUrls
	 */
	public void setPictureKeywordUrls(boolean pictureKeywordUrls) {
		this.pictureKeywordUrls = pictureKeywordUrls;
	}
	
}
