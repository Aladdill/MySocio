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
 * This class represents a Live Journal mood.
 * 
 * @author Troy Bourdon
 *
 */
public class Mood {
	private String name;
	private int id;
	
	/**
	 * Get the mood name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the mood name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the mood id.
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the mood name.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
