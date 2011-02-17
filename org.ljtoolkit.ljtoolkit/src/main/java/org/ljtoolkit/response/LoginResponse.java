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

package org.ljtoolkit.response;

import java.net.URL;
import java.util.List;

import org.ljtoolkit.data.FriendGroup;
import org.ljtoolkit.data.LJMenu;
import org.ljtoolkit.data.Mood;
import org.ljtoolkit.data.Picture;

public class LoginResponse extends BaseResponse {
	private String name;
	private String message;
	private int friendGroupMaximum;
	private List<FriendGroup> friendGroups;
	private int sharedJournalCount;
	private List<String> sharedJournals;	
	private int moodCount;
	private List<Mood> moods;
	private LJMenu ljMenu;
	private int pictureKeywordCount;
	private List<Picture> pictures;
	private URL defaultPictureUrl;
	private boolean isFastServer = false;
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMessage() { 
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<FriendGroup> getFriendGroups() {
		return friendGroups;
	}
	public void setFriendGroups(List<FriendGroup> friendGroups) {
		this.friendGroups = friendGroups;
	}
	
	public int getFriendGroupMaximum() {
		return friendGroupMaximum;
	}
	public void setFriendGroupMaximum(int friendGroupMaximum) {
		this.friendGroupMaximum = friendGroupMaximum;
	}
	public int getSharedJournalCount() {
		return sharedJournalCount;
	}
	public void setSharedJournalCount(int sharedJournalCount) {
		this.sharedJournalCount = sharedJournalCount;
	}
	public List<String> getSharedJournals() {
		return sharedJournals;
	}
	public void setSharedJournals(List<String> sharedJournals) {
		this.sharedJournals = sharedJournals;
	}
	public int getMoodCount() {
		return moodCount;
	}
	public void setMoodCount(int moodCount) {
		this.moodCount = moodCount;
	}
	public List<Mood> getMoods() {
		return moods;
	}
	public void setMoods(List<Mood> moods) {
		this.moods = moods;
	}
	public LJMenu getLjMenu() {
		return ljMenu;
	}
	public void setLjMenu(LJMenu ljMenu) {
		this.ljMenu = ljMenu;
	}
	public int getPictureKeywordCount() {
		return pictureKeywordCount;
	}
	public void setPictureKeywordCount(int pictureKeywordCount) {
		this.pictureKeywordCount = pictureKeywordCount;
	}	
	public List<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	public URL getDefaultPictureUrl() {
		return defaultPictureUrl;
	}
	public void setDefaultPictureUrl(URL defaultPictureUrl) {
		this.defaultPictureUrl = defaultPictureUrl;
	}
	public boolean isFastServer() {
		return isFastServer;
	}
	public void setFastServer(boolean isFastServer) {
		this.isFastServer = isFastServer;
	}
}
