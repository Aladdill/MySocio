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

/**
 * This class represents a Live Journal user.
 * 
 * @author Troy Bourdon
 *
 */
public class User {
	private String name;
	private String username;
	private String password;
	
	/**
	 * Get the user's name, not the user's username.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the user's name, not the user's username.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the user's username, not the user's name.
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set the user's username, not the user's name.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get the user's password.
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set the user's password.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
