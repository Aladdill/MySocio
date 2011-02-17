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

import java.util.Date;
import java.util.Map;

import org.ljtoolkit.enums.EventSecurity;

/**
 * The Event class represents the data associated with a Live Journal event.
 * 
 * @author Troy Bourdon
 *
 */
public class Event {
	private int id;
	private Date date;
	private String event;
	private EventSecurity security;
	private int allowmask;
	private String subject;
	private String poster;
	private Map<String, String> properties;
	
	/**
	 * A Live Journal event/item id.
	 * 
	 * @return event id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the event id.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get the event date of a post.
	 * 
	 * @return event date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Set the event date for posting.
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Get the actual contents of the Live Journal post.
	 * 
	 * @return event
	 */
	public String getEvent() {
		return event;
	}
	
	/**
	 * Set the event contents for a post to Live Journal.
	 * 
	 * @param event
	 */
	public void setEvent(String event) {
		this.event = event;
	}
		
	/**
	 * (Optional) Specifies who can read this post. Valid values are public (default), private and usemask. 
	 *  When value is usemask, viewability is controlled by the allowmask.
	 * 
	 * @return EventSecurity
	 */
	public EventSecurity getSecurity() {
		return security;
	}
	
	/**
	 * Set the security level to be used for a Live Journal post.
	 * 
	 * @param security The event security
	 */
	public void setSecurity(EventSecurity security) {
		this.security = security;
	}
	
	/**
	 * 
	 * Relevant when EventSecurity is USEMASK. A 32-bit unsigned integer representing which of the user's groups
	 * of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to read it.
	 * Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it
	 * Bit 31 is reserved.
	 * 
	 * @return int
	 * 
	 */
	public int getAllowmask() {
		return allowmask;
	}
	
	/**
	 * Set the allowmask.
	 * 
	 * @param allowmask
	 */
	public void setAllowmask(int allowmask) {
		this.allowmask = allowmask;
	}
	
	/**
	 * The subject for this post. Limited to 255 characters. No newlines.
	 *
	 * @return String
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Set the subject when posting an event.
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * The Live Journal username of the person posting the event.
	 * 
	 * @return String
	 */
	public String getPoster() {
		return poster;
	}
	
	/**
	 * Set the poster's username.
	 * 
	 * @param poster
	 */
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	/**
	 * A map of additional properties associated with an event.
	 * 
	 * @return Map
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	
	/**
	 * Set additional properties on an event when posting.
	 * 
	 * @param properties
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
