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

/**
 * This class represents the response returned from the Live Journal check friends
 * service call.
 * 
 * @author Troy Bourdon
 *
 */
public class CheckFriendsResponse extends BaseResponse {
	private String lastUpdate;
	private boolean newEntries;
	private Integer pollingInterval;
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public boolean isNewEntries() {
		return newEntries;
	}
	
	public void setNewEntries(boolean newEntries) {
		this.newEntries = newEntries;
	}
	
	public Integer getPollingInterval() {
		return pollingInterval;
	}
	
	public void setPollingInterval(Integer pollingInterval) {
		this.pollingInterval = pollingInterval;
	}	
}
