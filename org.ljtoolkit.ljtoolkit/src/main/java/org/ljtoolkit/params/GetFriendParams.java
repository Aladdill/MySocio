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
 * This class represents the parameters used for the 'getfriends' Live Journal service.
 * 
 * @author Troy Bourdon
 *
 */
public class GetFriendParams extends BaseParams {
	private boolean includeFriedOf;
	private boolean includeGroups;
	private boolean includeBirthdays;
	private int friendLimit;
	
	
	/**
	 * Gets the includeFriendOf setting
	 * @return includeFriendOf
	 */
	public boolean isIncludeFriedOf() {
		return includeFriedOf;
	}
	
	/**
	 * (Optional) If set to true, you will also get back the info from the "friendof" mode. Some clients show friends
	 * and friendof data in separate tabs/panes. If you're always going to load both, then use this flag
	 * (as opposed to a tabbed dialog approach, where the user may not go to the second tab and thus would not need
	 * to load the friendof data.) friendof request variables can be used
	 * @param includeFriedOf
	 */
	public void setIncludeFriedOf(boolean includeFriedOf) {
		this.includeFriedOf = includeFriedOf;
	}
	
	/**
	 * Gets the includeGroups setting
	 * @return <tt>true<tt> if groups are included 
	 */
	public boolean isIncludeGroups() {
		return includeGroups;
	}
	
	/**
	 * (Optional) If set to true, you will also get back the info from the "getfriendgroups" mode. See above setIncludeFriendOf
	 * for the reason why this would be useful.
	 * @param includeGroups
	 */
	public void setIncludeGroups(boolean includeGroups) {
		this.includeGroups = includeGroups;
	}
	
	/**
	 * Gets the inlcudeBirthdays setting
	 * @return includeBirthdays
	 */
	public boolean isIncludeBirthdays() {
		return includeBirthdays;
	}
	
	/**
	 * (Optional) If set to true, birthdays will be included with the friends results below
	 * @param includeBirthdays
	 */
	public void setIncludeBirthdays(boolean includeBirthdays) {
		this.includeBirthdays = includeBirthdays;
	}
	
	/**
	 * Gets the friend limit
	 * @return friendLimit
	 */
	public int getFriendLimit() {
		return friendLimit;
	}
	
	/**
	 * (Optional) If set to a numeric value greater than zero, this mode will only return the number of results indicated.
	 * Useful only for building pretty lists for display which might have a button to view the full list nearby
	 * @param friendLimit
	 */
	public void setFriendLimit(int friendLimit) {
		this.friendLimit = friendLimit;
	}
}
