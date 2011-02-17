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
 * Class that represents a Live Journal Friend Group.
 * 
 * @author Troy Bourdon
 *
 */
public class FriendGroup {
	private String name;
	private int sortOrder;
	private boolean isPublic = false;
	
	/**
	 * Get the friend group name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the friend group name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the sort-order of the friend group with number num. 
	 * An integer value between 0 and 255.
	 * 
	 * @return int
	 */
	public int getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * Set the sort order of the friend group
	 * 
	 * @param sortOrder
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * Is this group a public group.
	 * 
	 * @return boolean
	 */
	public boolean isPublic() {
		return isPublic;
	}
	
	/**
	 * Set if this group is a public group.
	 * 
	 * @param isPublic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
}
