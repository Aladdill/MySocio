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
import java.util.Date;
import java.util.Map;

/**
 * This class represents metadata that may be used when posting
 * a Live Journal event.
 * 
 * @author Troy Bourdon
 *
 */
public class PostEventMetadata {
	private String currentMood;
	private int currentMoodId;
	private String currentMusic;
	private boolean preformatted;
	private boolean commentsAllowed;
	private String pictureKeyword;
	private boolean backdated;
	private boolean noEmail;
	private String screening;
	private boolean unknown8bit;
	private boolean hasScreened;
	private int revisionNum;
	private Date commentAlterTime;
	private URL syndicationLink;
	private Date revisionTime;
	private String syndicationId;
	private Map<String, String> propsMap;
	
	

	public Map<String, String> getPropsMap() {
		return propsMap;
	}

	public void setPropsMap(Map<String, String> propsMap) {
		this.propsMap = propsMap;
	}

	/**
	 * Get the user's current mood
	 * @return currentMood
	 */
	public String getCurrentMood() {
		return currentMood;
	}
	
	/**
	 * Set the user's current mood
	 * @param currentMood
	 */
	public void setCurrentMood(String currentMood) {
		this.currentMood = currentMood;
	}
	
	/**
	 * Get the user's current mood id number
	 * @return currentMoodId
	 */
	public int getCurrentMoodId() {
		return currentMoodId;
	}
	
	/**
	 * Set the user's current mood id number
	 * @param currentMoodId
	 */
	public void setCurrentMoodId(int currentMoodId) {
		this.currentMoodId = currentMoodId;
	}
	
	/**
	 * Get the user's current music
	 * @return currentMusic
	 */
	public String getCurrentMusic() {
		return currentMusic;
	}
	
	/**
	 * Set the user's current music
	 * @param currentMusic
	 */
	public void setCurrentMusic(String currentMusic) {
		this.currentMusic = currentMusic;
	}
	
	/**
	 * Does the post contain HTML and should it be formatted.
	 * @return preformatted
	 */
	public boolean isPreformatted() {
		return preformatted;
	}
	
	/**
	 * If the post contains HTML should be set to true.
	 * @param preformatted
	 */
	public void setPreformatted(boolean preformatted) {
		this.preformatted = preformatted;
	}
	
	/**
	 * Are comments allowed for this post
	 * @return commentsAllowed
	 */
	public boolean isCommentsAllowed() {
		return commentsAllowed;
	}
	
	/**
	 * Set to false if no comments are allowed
	 * @param commentsAllowed
	 */
	public void setCommentsAllowed(boolean commentsAllowed) {
		this.commentsAllowed = commentsAllowed;
	}
	
	/**
	 * A keyword that should align to a defined picture
	 * @return pictureKeyword
	 */
	public String getPictureKeyword() {
		return pictureKeyword;
	}
	
	/**
	 * Set the picture keyword that aligns to a defined picture
	 * @param pictureKeyword
	 */
	public void setPictureKeyword(String pictureKeyword) {
		this.pictureKeyword = pictureKeyword;
	}
	
	/**
	 * Is this post backdated
	 * @return backdated
	 */
	public boolean isBackdated() {
		return backdated;
	}
	
	/**
	 * Set to true if this post shouldn't show up on people's friends list (because it occured in the past)
	 * @param backdated
	 */
	public void setBackdated(boolean backdated) {
		this.backdated = backdated;
	}
	
	/**
	 * Should the user receive comments to the post by email
	 * @return noEmail
	 */
	public boolean isNoEmail() {
		return noEmail;
	}
	
	/**
	 * Set to true if the user doesn't want to receive comments to this post via email
	 * @param noEmail
	 */
	public void setNoEmail(boolean noEmail) {
		this.noEmail = noEmail;
	}
	
	/**
	 * Like whoScreened: A=ALL, R=Remote needed(anonymous only), F=non=Friends, N=None, else
	 * use userprop
	 * @return screening
	 */
	public String getScreening() {
		return screening;
	}
	
	/**
	 * Like whoScreened: A=ALL, R=Remote needed(anonymous only), F=non=Friends, N=None, else
	 * use userprop
	 * @param screening
	 */
	public void setScreening(String screening) {
		this.screening = screening;
	}
	
	/**
	 * Does text have 8-bit data that's not in UTF-8
	 * @return unknown8bit
	 */
	public boolean isUnknown8bit() {
		return unknown8bit;
	}
	
	/**
	 * Set to true if text has 8-bit data that's not in UTF-8
	 * @param unknown8bit
	 */
	public void setUnknown8bit(boolean unknown8bit) {
		this.unknown8bit = unknown8bit;
	}
	
	/**
	 * Do comments to this post include screened comments
	 * @return hasScreened
	 */
	public boolean isHasScreened() {
		return hasScreened;
	}
	
	/**
	 * True if comments to this item include screened comments
	 * @param hasScreened
	 */
	public void setHasScreened(boolean hasScreened) {
		this.hasScreened = hasScreened;
	}
	
	/**
	 * Get the number of times the post has been edited
	 * @return revisionNum
	 */
	public int getRevisionNum() {
		return revisionNum;
	}
	
	/**
	 * Set the number of times the post has been edited
	 * @param revisionNum
	 */
	public void setRevisionNum(int revisionNum) {
		this.revisionNum = revisionNum;
	}
	
	/**
	 * Get the time of the last change to number of comments to this post (unix based)
	 * @return commentAlterTime
	 */
	public Date getCommentAlterTime() {
		return commentAlterTime;
	}
	
	/**
	 * Set the time of the last change to number of comments to this post (unix based)
	 * @param commentAlterTime
	 */
	public void setCommentAlterTime(Date commentAlterTime) {
		this.commentAlterTime = commentAlterTime;
	}
	
	/**
	 * Get the original URL of the syndication item
	 * @return syndicationLink
	 */
	public URL getSyndicationLink() {
		return syndicationLink;
	}
	
	/**
	 * Set the original URL of the syndication item
	 * @param syndicationLink
	 */
	public void setSyndicationLink(URL syndicationLink) {
		this.syndicationLink = syndicationLink;
	}
	
	/**
	 * Get the time of the last edit (unix based)
	 * @return The time of the last revision
	 */
	public Date getRevisionTime() {
		return revisionTime;
	}
	
	/**
	 * Set the time of the last edit (unix based)
	 * @param revisionTime
	 */
	public void setRevisionTime(Date revisionTime) {
		this.revisionTime = revisionTime;
	}
	
	/**
	 * Get the unique id of the syndication item
	 * @return syndicationId
	 */
	public String getSyndicationId() {
		return syndicationId;
	}
	
	/**
	 * Set the unique id of the syndication item
	 * @param syndicationId
	 */
	public void setSyndicationId(String syndicationId) {
		this.syndicationId = syndicationId;
	}
	
	
}
