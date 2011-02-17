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
 * A class representing an event property.
 * 
 * @author Troy Bourdon
 *
 */
public class EventProperty {

	private int eventId;
	private String name;
	private String value;
	
	/**
	 * Get a Live Journal event id.
	 * 
	 * @return int
	 */
	public int getEventId() {
		return eventId;
	}
	
	/**
	 * Set with a Live Journal event id.
	 * 
	 * @param eventId
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	/**
	 * Get the event property name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the event property name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the event property value.
	 * 
	 * @return String
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Set the event property value.
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
