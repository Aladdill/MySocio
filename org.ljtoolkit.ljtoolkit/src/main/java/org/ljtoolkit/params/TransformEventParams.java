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

import java.util.BitSet;

import org.ljtoolkit.data.PostEventMetadata;
import org.ljtoolkit.enums.EventAction;
import org.ljtoolkit.enums.EventSecurity;

/**
 * 
 * This class represents the parameter object used to post an event to Live Journal.
 * The way in which the values are set in the class will determine the characteristics
 * of the post.
 * 
 * @author Troy Bourdon
 *
 */
public class TransformEventParams extends BaseEventParams {
	private int itemId;
	private String event;
	private String subject;
	private EventSecurity security;
	private BitSet allowmask;
	private PostEventMetadata metadata;
	private EventAction action;

	/**
	 * Constructor which sets up some default state.
	 * 
	 * Default: Security -> Private
	 */
	public TransformEventParams() {
		setSecurity(EventSecurity.PRIVATE);
		setLineEndings(BaseEventParams.PC_LINE_ENDING);
	}
	
	/**
	 * Get the unique ItemID of the item being modified or deleted
	 * @return itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * Set the unique ItemID of the item being modified or deleted
	 * @param itemId
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * The event text the user is submitting, may be a revision if this is an edit post event.
	 * @return event
	 */
	public String getEvent() {
		return event;
	}
	
	/**
	 * If this is an original post this will be the text of the post. If this is a revision/update
	 * this will be the revised text. If this is a delete this should be null. Carriage returns are okay
	 * (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are removed internally to make everything into Unix-style 
	 * line-endings (just \ns). Posts may also contain HTML, but be aware that the LiveJournal server
	 * converts newlines to HTML <BR>s when displaying them, so your client should not try to insert
	 * these itself.
	 * @param event
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	
	/**
	 * Get the subject of this post
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * The subject for this post. Limited to 255 characters. No newlines.
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Get the EventSecurity for this post.
	 * @return security
	 */
	public EventSecurity getSecurity() {
		return security;
	}
	
	/**
	 * (Optional) Specifies who can read this post. Valid values are public (default), private and usemask.
	 * When value is usemask, viewability is controlled by the allowmask.
	 * @param security
	 */
	public void setSecurity(EventSecurity security) {
		this.security = security;
	}
	
	/**
	 * Get the allowmask for this post
	 * @return allowmask
	 */
	public BitSet getAllowmask() {
		return allowmask;
	}
	
	/**
	 * Relevant when security is usemask. A 32-bit unsigned integer representing which of the user's groups
	 * of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to read it.
	 * Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it.
	 * Bit 31 is reserved.
	 * @param allowmask
	 */
	public void setAllowmask(BitSet allowmask) {
		this.allowmask = allowmask;
	}

	/**
	 * Get the post event metadata
	 * @return The metadata associated with the post event service
	 */
	public PostEventMetadata getMetadata() {
		return metadata;
	}

	/**
	 * Set the post event metadata
	 * @param metadata
	 */
	public void setMetadata(PostEventMetadata metadata) {
		this.metadata = metadata;
	}

	public EventAction getAction() {
		return action;
	}

	public void setAction(EventAction action) {
		this.action = action;
	}
	
	
}
