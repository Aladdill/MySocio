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

package org.ljtoolkit.services;

import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ljtoolkit.data.Challenge;
import org.ljtoolkit.data.Event;
import org.ljtoolkit.data.Friend;
import org.ljtoolkit.data.FriendGroup;
import org.ljtoolkit.data.User;
import org.ljtoolkit.exceptions.LiveJournalServiceException;
import org.ljtoolkit.params.GetEventParams;
import org.ljtoolkit.params.GetFriendParams;
import org.ljtoolkit.params.LoginParams;
import org.ljtoolkit.params.TransformEventParams;
import org.ljtoolkit.response.CheckFriendsResponse;
import org.ljtoolkit.response.LoginResponse;

/**
 * This interface represents an aggregation of the various Live Journal service protocols.
 * The protocols represented are:
 * <p><ul>
 * <li>getchallenge</li>
 * <li>login</li>
 * <li>checkfriends</li>
 * <li>getfriends</li>
 * <li>getfriendgroups</li>
 * <li>friendof</li>
 * <li>editfriends</li>
 * <li>editfriendgroups</li>
 * <li>getdaycounts</li>
 * <li>getevents</li>
 * <li>editevent</li></ul>
 * 
 * @author Troy Bourdon
 *
 */
public interface ILiveJournalService {
		
	/**
	 * This method represents the 'getchallenge' Live Journal service.
	 * 
	 * @return challenge
	 * @throws LiveJournalServiceException
	 */
	public Challenge getChallenge() throws LiveJournalServiceException;
	
	/**
	 * This method is used to login to the Live Journal server.
	 * 
	 * @param user
	 * @param params
	 * @return loginResponse
	 * @throws LiveJournalServiceException
	 */
	public LoginResponse login(User user, LoginParams params) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'checkfriends' Live Journal service.
	 * 
	 * @param lastUpdate
	 * @param groupMask
	 * @return checkFriendsResponse
	 * @throws LiveJournalServiceException
	 */
	public CheckFriendsResponse checkFriends(String lastUpdate, BitSet groupMask) throws LiveJournalServiceException;

	/**
	 * This method represents the 'getfriends' Live Journal service.
	 * 
	 * @param params
	 * @return list of friends
	 * @throws LiveJournalServiceException
	 */
	public List<Friend> getFriends(GetFriendParams params) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'getfreindgroups' Live Journal service.
	 * 
	 * @return list of friend groups
	 * @throws LiveJournalServiceException
	 */
	public List<FriendGroup> getFriendGroups() throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'friendof' Live Journal service.
	 * 
	 * @param limit
	 * @return list of friends
	 * @throws LiveJournalServiceException
	 */
	public List<Friend> getFriendsOf(Integer limit) throws LiveJournalServiceException;

	/**
	 * This method represents the 'editfreinds' Live Journal service [delete].
	 * 
	 * @param friend
	 * @throws LiveJournalServiceException
	 */
	public void deleteFriend(Friend friend) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editfriends' Live Journal service [add].
	 * 
	 * @param friend
	 * @return add friend
	 * @throws LiveJournalServiceException
	 */
	public Friend addFriend(Friend friend) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editfriends' Live Journal service [add friend list].
	 * 
	 * @param friends
	 * @return list of added friends
	 * @throws LiveJournalServiceException
	 */
	public List<Friend> addFriends(List<Friend> friends) throws LiveJournalServiceException;
	
	/**
	 * This method allows for the editing of the friend groupmask.
	 * 
	 * @param friend
	 * @throws LiveJournalServiceException
	 */
	public void editFriendGroupmask(Friend friend) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editfriendgroups' Live Journal service [delete].
	 * 
	 * @param groupNumber
	 * @throws LiveJournalServiceException
	 */
	public void deleteFriendGroup(Integer groupNumber) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editfriendgroups' Live Journal service.
	 * 
	 * @param groupNumber
	 * @param groupName
	 * @throws LiveJournalServiceException
	 */
	public void setFriendGroupName(Integer groupNumber, String groupName) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editfriendgroups' Live Journal service.
	 * 
	 * @param groupNumber
	 * @param sortNumber
	 * @throws LiveJournalServiceException
	 */
	public void setFriendGroupSorting(Integer groupNumber, Integer sortNumber) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editfriendgroups' Live Journal service.
	 * 
	 * @param groupNumber
	 * @param isPublic
	 * @throws LiveJournalServiceException
	 */
	public void setFriendGroupPublic(Integer groupNumber, boolean isPublic) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'getdaycounts' Live Journal service.
	 * 
	 * @param username
	 * @return map of dates(key) and counts(value)
	 * @throws LiveJournalServiceException
	 */
	public Map<Date, Integer> getDayCounts(String username) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'getevents' Live Journal service.
	 * 
	 * @param params
	 * @return list of events.
	 * @throws LiveJournalServiceException
	 */
	public List<Event> getEvents(GetEventParams params) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'editevent' Live Journal service.
	 * 
	 * @param params
	 * @throws LiveJournalServiceException
	 */
	public void editEvent(TransformEventParams params) throws LiveJournalServiceException;
	
	/**
	 * This method represents the 'postevent' Live Journal service.
	 * 
	 * @param params
	 * @return id of posted event
	 * @throws LiveJournalServiceException
	 */
	public int postEvent(TransformEventParams params) throws LiveJournalServiceException;

}
