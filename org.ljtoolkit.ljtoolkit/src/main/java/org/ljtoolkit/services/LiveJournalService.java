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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ljtoolkit.data.Challenge;
import org.ljtoolkit.data.Event;
import org.ljtoolkit.data.EventProperty;
import org.ljtoolkit.data.Friend;
import org.ljtoolkit.data.FriendGroup;
import org.ljtoolkit.data.Mood;
import org.ljtoolkit.data.Picture;
import org.ljtoolkit.data.PostEventMetadata;
import org.ljtoolkit.data.User;
import org.ljtoolkit.enums.EventAction;
import org.ljtoolkit.enums.EventSecurity;
import org.ljtoolkit.enums.SelectType;
import org.ljtoolkit.exceptions.LiveJournalServiceException;
import org.ljtoolkit.params.GetEventParams;
import org.ljtoolkit.params.GetFriendParams;
import org.ljtoolkit.params.LoginParams;
import org.ljtoolkit.params.TransformEventParams;
import org.ljtoolkit.response.CheckFriendsResponse;
import org.ljtoolkit.response.LoginResponse;
import org.ljtoolkit.utils.LiveJournalUtils;
import org.ljtoolkit.utils.MD5;

/**
 * This class provides the live journal FLAT service implementation. The methods
 * in this class wrap the following Live Journal Flat Protocols:
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
 * @see ILiveJournalService
 *
 */
public class LiveJournalService implements ILiveJournalService {
	private static LiveJournalService instance;
	private User user;
	private PostMethod post;

	private Log logger = LogFactory.getLog(this.getClass());

	public static LiveJournalService getInstance() {
		if(instance == null)
			return new LiveJournalService();
		else
			return instance;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
		
	private void initPost() {
		post = new PostMethod("http://www.livejournal.com/interface/flat");
	}
	
	private void clearPost() {
		post = null;
	}
	
	private PostMethod getPost() {
		return post;
	}
	
	/**
	 * This method provides the implementation of the 'getchallenge' Live Journal service.
	 * 
	 * @return the challenge returned from the Live Journal service
	 * @throws LiveJournalServiceException
	 * @see Challenge
	 */
	public Challenge getChallenge() throws LiveJournalServiceException {
		initPost();
		Map<String, String> map = getChallengeMap();
		Challenge challenge = new Challenge();
		
		challenge.setChallenge(map.get("challenge"));
		challenge.setSuccess(map.get("success"));
		challenge.setErrMsg(map.get("errmsg"));
		challenge.setAuthScheme(map.get("auth_scheme"));
		challenge.setExpireTime(LiveJournalUtils.toDate(map.get("expire_time")));
		challenge.setServerTime(LiveJournalUtils.toDate(map.get("server_time")));
		
		clearPost();
		return challenge;
	}

	/**
	 * This method provides the implementation of the 'login' Live Journal service.
	 * 
	 * @param user The user credentials
	 * @param params The login parameters
	 * @throws LiveJournalServiceException
	 * @return The login response returned from the Live Journal service
	 * @see LoginResponse
	 */
	public LoginResponse login(User user, LoginParams params) throws LiveJournalServiceException {
		this.user = user;
		return login(user, getChallenge(), params);		
	}
	private LoginResponse login(User user, Challenge challenge, LoginParams params) throws LiveJournalServiceException {		
		initPost();
		getPost().setParameter("clientversion", "Java-Lifeque/0.1.0");		
		if(params.getMoods() != null)
			getPost().setParameter("getmoods", params.getMoods().toString());
		if(params.getMenus())
			getPost().setParameter("getmenus", "1");
		if(params.getPictureKeywords())
			getPost().setParameter("getpickws", "1");
		if(params.getPictureKeywordUrls())
			getPost().setParameter("getpickwurls", "1");
			
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "login");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to login to LiveJournal");
		}
		
		LoginResponse loginResponse = new LoginResponse();
		
		clearPost();
		return loginResponse;
	}
	
	/**
	 * This method provides the implementation for the 'editfriends' (groupmask) Live Journal
	 * service.
	 * 
	 * @param friend The friend whose groupmask is to be edited
	 * @throws LiveJournalServiceException
	 * @see Friend
	 */
	public void editFriendGroupmask(Friend friend) throws LiveJournalServiceException {
		editFriendGroupmask(friend, getChallenge());
	}		
	private void editFriendGroupmask(Friend friend, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		getPost().setParameter(
				"editfriend_groupmask_" + friend.getUser(), 
				friend.getGroupMask().toString());
				
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriendgroups");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to edit friend groupmask");
		}
		
		clearPost();
	}
	
	/**
	 * This method provides the implementation for the 'editfriends' (delete) Live Journal
	 * service.
	 * 
	 * @param groupNumber The group number of the friend group to be deleted
	 * @throws LiveJournalServiceException
	 */
	public void deleteFriendGroup(Integer groupNumber) throws LiveJournalServiceException {
		deleteFriendGroup(groupNumber, getChallenge());
	}
	private void deleteFriendGroup(Integer groupNumber, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		getPost().setParameter("efg_delete_" + groupNumber.toString(), "");	
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriendgroups");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to delete friend group");
		}
		
		clearPost();
	}

	/**
	 * This method provides the implementation for the 'editfriends' (set) Live Journal service.
	 * 
	 * @param groupNumber The group number of the friend group to be set
	 * @param groupName The group name
	 * @throws LiveJournalServiceException 
	 */
	public void setFriendGroupName(Integer groupNumber, String groupName) throws LiveJournalServiceException {
		setFriendGroupName(groupNumber, groupName, getChallenge());
	}
	private void setFriendGroupName(Integer groupNumber, String groupName, Challenge challenge) 
		throws LiveJournalServiceException {
		initPost();
		getPost().setParameter("efg_set_" + groupNumber.toString() + "_name", groupName);	
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriendgroups");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to set friend group name");
		}
		
		clearPost();
	}
	
	/**
	 * This methods sets the sort order for friend groups.
	 * 
	 * @param groupNumber The group number to set sort order
	 * @param sortNumber The sort order (0-255), default of 50
	 * @throws LiveJournalServiceException
	 */
	public void setFriendGroupSorting(Integer groupNumber, Integer sortNumber) throws LiveJournalServiceException {
		setFriendGroupSorting(groupNumber, sortNumber, getChallenge());
	}	
	private void setFriendGroupSorting(Integer groupNumber, Integer sortNumber, Challenge challenge) 
		throws LiveJournalServiceException {
		initPost();
		getPost().setParameter("efg_set_" + groupNumber.toString() + "_sort", sortNumber.toString());
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriendgroups");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to set friend group sorting");
		}
		
		clearPost();
	}
	
	/**
	 * This method sets the public flag for a friend group. If set to true others can see the name
	 * of the group and the people who are in the group.
	 * 
	 * @param groupNumber The group number to set public flag
	 * @param isPublic The public flag (true or false)
	 * @throws LiveJournalServiceException
	 */
	public void setFriendGroupPublic(Integer groupNumber, boolean isPublic) throws LiveJournalServiceException {
		setFriendGroupPublic(groupNumber, isPublic, getChallenge());
	}	
	private void setFriendGroupPublic(Integer groupNumber, boolean isPublic, Challenge challenge) 
		throws LiveJournalServiceException {
		initPost();
		if(isPublic)
			getPost().setParameter("efg_set_" + groupNumber.toString() + "_public", "1");
		else
			getPost().setParameter("efg_set_" + groupNumber.toString() + "_public", "0");
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriendgroups");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to set friend group public");
		}
		
		clearPost();
	}
	
	/**
	 * This method deletes a friend from the friend list.
	 * 
	 * @param friend The friend to be removed from friend list
	 * @throws LiveJournalServiceException
	 */
	public void deleteFriend(Friend friend) throws LiveJournalServiceException {
		deleteFriend(friend, getChallenge());
	}	
	private void deleteFriend(Friend friend, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		getPost().setParameter("editfriend_delete_" + friend.getUser(), "1");		
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriends");

		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to delete friend");
		}
		
		clearPost();
	}
	
	/**
	 * This method adds a friend to the friend list.
	 * 
	 * @param friend The friend to add
	 * @return If successful, the added friend
	 * @throws LiveJournalServiceException
	 */
	public Friend addFriend(Friend friend) throws LiveJournalServiceException {
		return addFriend(friend, getChallenge());
	}	
	private Friend addFriend(Friend friend, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		getPost().setParameter("editfriend_add_1_user", friend.getUser());
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editfriends");
		
		if((responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) || 
				!responseMap.get("friends_added").equalsIgnoreCase("1")) {
			throw new LiveJournalServiceException("Unable to add friend " + friend.getUser());
		}		
		
		Friend rtnFriend = new Friend();
		rtnFriend.setUser(responseMap.get("friend_1_user"));
		rtnFriend.setName(responseMap.get("friend_1_name"));
		
		clearPost();
		return rtnFriend;
	}
	
	/**
	 * This method is used to add a list of friends to Live Journal.
	 * 
	 * @param friends The List of friends to add
	 * @return If successful, the list of added friends
	 * @throws LiverJournalServiceException
	 */
	public List<Friend> addFriends(List<Friend> friends) throws LiveJournalServiceException {
		return addFriends(friends, getChallenge());
	}
	private List<Friend> addFriends(List<Friend> friends, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		getPost().setParameter("mode", "editfriends");
		
		int addIdx = 1;
		for(Friend addFriend : friends) {
			Integer idx = new Integer(addIdx);
			getPost().setParameter("editfriend_add_" + idx.toString() + "_user", addFriend.getUser());
		}
		
		Map<String, String> responseMap  = fetchResponseFromLiveJournal(user, challenge, "editfriends");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to add friends");
		}		
		
		List<Friend> rtnFriends = new ArrayList<Friend>();
		int numAdded = new Integer(responseMap.get("friends_added")).intValue();
		for(int i = 1; i <= numAdded; i++) {
			Friend friend = new Friend();
			Integer idx = new Integer(i);
			String username = responseMap.get("friend_" + idx.toString() + "_user");
			String name = responseMap.get("friend_" + idx.toString() + "_name");
			friend.setUser(username);
			friend.setName(name);
			rtnFriends.add(friend);
		}
		
		clearPost();
		return rtnFriends;		
	}
		
	/**
	 * This method checks to see if the friends list has been updated.
	 * 
	 * @param lastUpdate The time of the last update
	 * @param groupMask The bit set the represents the group mask
	 * @return The response for the check friends action
	 * @throws LiveJournalServiceException
	 */
	public CheckFriendsResponse checkFriends(String lastUpdate, BitSet groupMask) throws LiveJournalServiceException {
		return checkFriends(lastUpdate, groupMask, getChallenge());
	}	
	private CheckFriendsResponse checkFriends(String lastUpdate, BitSet groupMask, Challenge challenge) 
		throws LiveJournalServiceException {
		initPost();
		
		getPost().setParameter("lastupdate", lastUpdate);
		getPost().setParameter("mask", groupMask.toString());

		CheckFriendsResponse response = new CheckFriendsResponse();

		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "checkfriends");
		response.setSuccess(responseMap.get("success"));
		response.setLastUpdate(responseMap.get("lastupdate"));
		
		if(responseMap.get("new").equalsIgnoreCase("1"))
			response.setNewEntries(true);
		else
			response.setNewEntries(false);	
		
		response.setPollingInterval(new Integer(responseMap.get("interval")));
	
		if(response.getSuccess() != null && response.getSuccess().equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to check friends");
		}
		
		clearPost();
		return response;
	}

	/**
	 * This method returns a list of Live Journal users that lists this user
	 * as a friend.
	 * 
	 * @param limit The limit on the number returned
	 * @return The list of friends listing this friend as their friend
	 * @throws LiveJournalException
	 */
	public List<Friend> getFriendsOf(Integer limit) throws LiveJournalServiceException {
		return getFriendsOf(limit, getChallenge());
	}
	private List<Friend> getFriendsOf(Integer limit, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		
		getPost().setParameter("friendoflimit", limit.toString());
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "friendof");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to check friendof list");
		}

		int friendCount = NumberUtils.toInt(responseMap.get("friendof_count"));
		List<Friend> friends = new ArrayList<Friend>();
		
		if(friendCount != 0) { // iterate and populate			
			for(int i = 1; i <= friendCount; i++) {
				String userKey = "friendof_" + i + "_user";
				String nameKey = "friendof_" + i + "_name";
				String bgKey = "friendof_" + i + "_bg";
				String fgKey = "friendof_" + i + "_fg";
				String accountTypeKey = "friendof_" + i + "_type";
				String statusTypeKey = "friendof_" + i + "_status";
				
				Friend friend = new Friend();
				
				friend.setUser(responseMap.get(userKey));
				friend.setName(responseMap.get(nameKey));
				friend.setBgColor(responseMap.get(bgKey));
				friend.setFgColor(responseMap.get(fgKey));
				friend.setAccountType(responseMap.get(accountTypeKey));
				friend.setAccountStatus(responseMap.get(statusTypeKey));
				
				friends.add(friend);
			}
						
		}

		clearPost();
		return friends;		
	}

	/**
	 * This method gets the list of friend groups for this user.
	 * 
	 * @return The list of friend groups
	 * @throws LiveJournalServiceException
	 */
	public List<FriendGroup> getFriendGroups() throws LiveJournalServiceException {
		return getFriendGroups(getChallenge());
	}
	private List<FriendGroup> getFriendGroups(Challenge challenge) throws LiveJournalServiceException {
		initPost();
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "getfriendgroups");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to check friendof list");
		}

		List<FriendGroup> groups = new ArrayList<FriendGroup>();
		int friendGroupMaxNum = new Integer(responseMap.get("frgrp_maxnum")).intValue();
		
		for(int i = 1; i <= friendGroupMaxNum; i++) {
			String nameKey = "frgrp_" + i + "_name";
			String orderKey = "frgrp_" + i + "_sortorder";
			String publicKey = "frgrp_" + i + "_public";
			
			if(responseMap.get(nameKey) != null) {
				FriendGroup group = new FriendGroup();
				group.setName(responseMap.get(nameKey));
				group.setSortOrder(NumberUtils.toInt(responseMap.get(orderKey)));
				
				if(responseMap.get(publicKey) != null && 
						responseMap.get(publicKey).equalsIgnoreCase("1")) {
					group.setPublic(true);
				}
				
				groups.add(group);
			}
		}
		
		clearPost();
		return groups;
	}
	
	/**
	 * This method gets the friend list for this user.
	 * 
	 * @param params Parameters used in the query
	 * @return The list of friends
	 * @throws LiveJournalServiceException
	 */
	public List<Friend> getFriends(GetFriendParams params) throws LiveJournalServiceException {
		return getFriends(params, getChallenge());
	}	
	private List<Friend> getFriends(GetFriendParams params, Challenge challenge) throws LiveJournalServiceException {
		initPost(); 
		
		if(params.isIncludeFriedOf())
			getPost().setParameter("includefriendof", "1");
		else
			getPost().setParameter("includefriendof", "0");
		
		if(params.isIncludeGroups())
			getPost().setParameter("includegroups", "1");
		else
			getPost().setParameter("includegroups", "0");
		
		if(params.isIncludeBirthdays())
			getPost().setParameter("includebdays", "1");
		else
			getPost().setParameter("includebdays", "0");
		
		getPost().setParameter(
				"friendlimit", new Integer(params.getFriendLimit()).toString());

		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "getfriends");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to check friendof list");
		}

		int friendCount = NumberUtils.toInt(responseMap.get("friend_count"));
		List<Friend> friends = new ArrayList<Friend>();
		
		if(friendCount != 0) { // iterate and populate			
			for(int i = 1; i <= friendCount; i++) {
				String userKey = "friend_" + i + "_user";
				String nameKey = "friend_" + i + "_name";
				String birthdayKey = "friend_" + i + "_birthday";
				String bgKey = "friend_" + i + "_bg";
				String fgKey = "friend_" + i + "_fg";
				String groupMaskKey = "friend_" + i + "_groupmask";
				String accountTypeKey = "friend_" + i + "_type";
				String statusTypeKey = "friend_" + i + "_status";
				
				Friend friend = new Friend();
				
				friend.setUser(responseMap.get(userKey));
				friend.setName(responseMap.get(nameKey));
				friend.setBirthday(LiveJournalUtils.toDate(responseMap.get(birthdayKey)));
				friend.setBgColor(responseMap.get(bgKey));
				friend.setFgColor(responseMap.get(fgKey));
				friend.setGroupMask(responseMap.get(groupMaskKey));
				friend.setAccountType(responseMap.get(accountTypeKey));
				friend.setAccountStatus(responseMap.get(statusTypeKey));
				
				friends.add(friend);
			}
		}

		clearPost();
		return friends;
	}

	/**
	 * This method returns the number of journal entries per day.
	 * 
	 * @param username The user's username
	 * @return A map of date keys and number values
	 * @throws LiveJournalServiceException
	 */
	public Map<Date, Integer> getDayCounts(String username) throws LiveJournalServiceException {
		return getDayCounts(username, getChallenge());
	}
	private Map<Date, Integer> getDayCounts(String username, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		
		if(username != null)
			getPost().setParameter("userjournal", username);
		
		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "getdaycounts");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to get the day counts for user: " + username);
		}

		Map<Date, Integer> dayCountMap = new HashMap<Date, Integer>();

		for(String key : responseMap.keySet()) {
			if(!key.equalsIgnoreCase("success") && !key.equalsIgnoreCase("errmsg")) {
				Date date = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Integer value = new Integer(responseMap.get(key));
				try {
					date = formatter.parse(key);
					dayCountMap.put(date, value);
				} catch (ParseException e) {
					throw new LiveJournalServiceException("Unable to parse the date for key: " + key, e);
				}
			}
		}
		
		clearPost();
		return dayCountMap;
	}

	/**
	 * This method gets the events (entries) for the users journal.
	 * 
	 * @param params Parameters used to control the query
	 * @return The list of events
	 * @throws LiveJournalException
	 */
	public List<Event> getEvents(GetEventParams params) throws LiveJournalServiceException {
		return getEvents(params, getChallenge());
	}
	private List<Event> getEvents(GetEventParams params, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		buildGetEventPostParameters(params);

		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "getevents");
		
		if(responseMap == null || responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to get the requested events");
		}

		int eventCount = NumberUtils.toInt(responseMap.get("events_count"));
		List<Event> events = new ArrayList<Event>();
		
		if(eventCount != 0) { // iterate and populate			
			for(int i = 1; i <= eventCount; i++) {
				String idKey = "events_" + i + "_itemid";
				String dateKey = "events_" + i + "_eventtime";
				String eventKey = "events_" + i + "_event";
				String securityKey = "events_" + i + "_security";
				String allowmaskKey = "events_" + i + "_allowmask";
				String subjectKey = "events_" + i + "_subject";
				String posterKey = "events_" + i + "_poster";
				
				Event event = new Event();
				
				event.setId(NumberUtils.toInt(responseMap.get(idKey)));
				event.setDate(LiveJournalUtils.toDateFromDateString(responseMap.get(dateKey)));
				event.setEvent(responseMap.get(eventKey));
				populateEventSecurity(event, responseMap.get(securityKey));
				event.setAllowmask(NumberUtils.toInt(responseMap.get(allowmaskKey)));
				event.setSubject(responseMap.get(subjectKey));
				event.setPoster(responseMap.get(posterKey));
				
				events.add(event);
			}
			
			List<EventProperty> props = buildPropertiesList(responseMap);
			addPropertiesToEvents(events, props);			
		}
		
		clearPost();
		
		return events;
	}

	/**
	 * This method provides the implementation for the Live Journal 'editevent' service. This
	 * method provides the ability to update and delete journal entries.
	 * 
	 * @param params The transform event parameters populated for the action
	 * @throws Live JournalServiceException
	 * @see ILiveJournalService
	 *  
	 */
	public void editEvent(TransformEventParams params) throws LiveJournalServiceException {
		editEvent(params, getChallenge());
	}
	private void editEvent(TransformEventParams params, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		buildEditEventPostParameters(params);

		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "editevent");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to edit the given event: " + params.getItemId());
		}

		clearPost();
	}

	/**
	 * This method is used to post an event to Live Journal. Before making this call the {@link TransformEventParams}
	 * class needs to be setup in an appropriate manner. See the documentation for the
	 * {@link org.ljtoolkit.params.TransformEventParams} class for more details.
	 * 
	 * @param params TransformEventParams
	 * @return eventId
	 * @throws LiveJournalException
	 */
	public int postEvent(TransformEventParams params) throws LiveJournalServiceException {
		return postEvent(params, getChallenge());
	}
	private int postEvent(TransformEventParams params, Challenge challenge) throws LiveJournalServiceException {
		initPost();
		params.setAction(EventAction.POST);
		buildTransformEventPostParameters(params);

		Map<String, String> responseMap = fetchResponseFromLiveJournal(user, challenge, "postevent");
		
		if(responseMap != null && responseMap.get("success").equalsIgnoreCase("FAIL")) {
			throw new LiveJournalServiceException("Unable to post the given event: " + params.getItemId());
		}

		clearPost();
		return NumberUtils.toInt(responseMap.get("itemid"));
	}

	private Map<String, String> fetchResponseFromLiveJournal(User user, Challenge challenge, String mode) 
		throws LiveJournalServiceException {
		assert(user != null);
		HttpService httpService = HttpService.getInstance();
		
		//Challenge challenge = getChallenge();
		buildBasePostParameters(user, challenge, mode);
		//logPostParameters();
		
		Map<String, String> rtnMap = null;		
	    try {
			if(httpService.executeMethod(getPost()) != 200) {
				StringBuffer errBuffer = new StringBuffer();
				errBuffer.append("Falid to authenticate with parameters...\n");
				errBuffer.append("LifequeUser: " + user.getUsername() + "\n");
				errBuffer.append("Challenge: " + challenge.getChallenge() + "\n");
				throw new LiveJournalServiceException(errBuffer.toString());
			}
			
			rtnMap = LiveJournalUtils.convertFlatResponseToMap(getPost().getResponseBodyAsString());
			
			// Display response
//			System.out.println("Response body: ");
//			System.out.println(getPost().getResponseBodyAsString());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        // Release current connection to the connection pool once you are done
	        getPost().releaseConnection();
		}
		
		return rtnMap;		
	}
	
	private void logPostParameters() {
		NameValuePair[] params = getPost().getParameters();
		
		for(int i = 0; i < params.length; i++) {
			System.out.println("Parameter Name: " + params[i].getName() + " Parameter Value: " + params[i].getValue());
		}
	}
	private void buildBasePostParameters(User user, Challenge challenge, String mode) {
		String encodedPassword = MD5.getEncodedHex(user.getPassword());
		String response = MD5.getEncodedHex(challenge.getChallenge() + encodedPassword);

		getPost().setParameter("mode", mode);
		getPost().setParameter("user", user.getUsername());
		getPost().setParameter("auth_method", "challenge");
		getPost().setParameter("auth_challenge", challenge.getChallenge());
		getPost().setParameter("auth_response", response);
	}

	private Map<String, String> getChallengeMap() throws LiveJournalServiceException {
		HttpService httpService = HttpService.getInstance();
		getPost().setParameter("mode", "getchallenge");

		Map<String, String> challengeMap = new HashMap<String, String>();
		try {
			if(httpService.executeMethod(getPost()) != 200) {
				throw new LiveJournalServiceException("Falid challenge request\n");
			}
			challengeMap = LiveJournalUtils.convertFlatResponseToMap(getPost().getResponseBodyAsString());
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			getPost().releaseConnection();
		}

		return challengeMap;
	}	

	private void populateLogin(LoginResponse response, Map<String, String> responseMap) 
		throws LiveJournalServiceException {
		// Populate the single values
		response.setName(responseMap.get("name"));
		response.setMessage(responseMap.get("message"));
		response.setErrMsg(responseMap.get("errmsg"));
		response.setSuccess(responseMap.get("success"));
		
		// Populate the friend groups
		populateFriendGroups(response, responseMap);		
		
		// Populate shared journals
		populateSharedJournals(response, responseMap);
		
		// Populate moods
		populateMoods(response, responseMap);
		
		// Populate menus
		populateMenus(response, responseMap);
		
		// Populate picture keywords
		populatePictures(response, responseMap);
		
		// Populate default picture URL
		populateDefaultPictureUrl(response, responseMap);
		
		// Populate default fast server
		populateFastServer(response, responseMap);
	}

	private void populateFriendGroups(LoginResponse login, Map<String, String> responseMap) {
		int maxFriendGroups = 0;
		
		if(responseMap.get("frgrp_maxnum") != null)
			maxFriendGroups = NumberUtils.toInt(responseMap.get("frgrp_maxnum"));
		
		login.setFriendGroupMaximum(maxFriendGroups);
		if(maxFriendGroups != 0) {
			try {
				login.setFriendGroups(
						LiveJournalService.getInstance().getFriendGroups());
			} catch (LiveJournalServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
	}
	
	private void populateSharedJournals(LoginResponse login, Map<String, String> responseMap) {
		int accessCount = 0;
		
		if(responseMap.get("access_count") != null)
			accessCount = NumberUtils.toInt(responseMap.get("access_count"));
		
		login.setSharedJournalCount(accessCount);
		if(accessCount != 0) { //iterate and populate
			List<String> journals = new ArrayList<String>();
			
			for(int i = 1; i <= accessCount; i++) {
				String testKey = "access_" + i;
				
				if(responseMap.get(testKey) != null) {
					String journal = responseMap.get(testKey);			
					journals.add(journal);
				}
			}
			login.setSharedJournals(journals);
		}						
	}
	
	private void populateMoods(LoginResponse login, Map<String, String> responseMap) {
		int moodCount = 0;
		
		if(responseMap.get("mood_count") != null)
			moodCount = NumberUtils.toInt(responseMap.get("mood_count"));
		
		login.setMoodCount(moodCount);
		if(moodCount != 0) { //iterate and populate
			List<Mood> moods = new ArrayList<Mood>();
			
			for(int i = 1; i <= moodCount; i++) {
				String testIdKey = "mood_" + i + "_id";
				String testNameKey = "mood_" + i + "_name";
				
				if(responseMap.get(testIdKey) != null) {
					Mood mood = new Mood();
					mood.setId(NumberUtils.toInt(responseMap.get(testIdKey)));
					mood.setName(responseMap.get(testNameKey));
					
					moods.add(mood);
				}
			}
			
			login.setMoods(moods);
		}						
	}
	
	private void populateMenus(LoginResponse login, Map<String, String> responseMap) {
		//TODO Implementation deferred
	}
	
	private void populatePictures(LoginResponse login, Map<String, String> responseMap) 
		throws LiveJournalServiceException {
		int pictureKeywordCount = 0;
		
		if(responseMap.get("pickw_count") != null)
			pictureKeywordCount = NumberUtils.toInt(responseMap.get("pickw_count"));
		
		login.setPictureKeywordCount(pictureKeywordCount);
		if(pictureKeywordCount != 0) { //iterate and populate
			List<Picture> pictures = new ArrayList<Picture>();
			
			for(int i = 1; i <= pictureKeywordCount; i++) {
				String testKey = "pickw_" + i;
				String testUrlKey = "pickwurl_" + i;
				
				if(responseMap.get(testKey) != null) {
					Picture picture = new Picture();
					picture.setKeyword(responseMap.get(testKey));
					URL url;
					
					try {
						url = new URL(responseMap.get(testUrlKey));
						picture.setUrl(url);
					} catch (MalformedURLException e) {
						throw new LiveJournalServiceException("Unable to populate pictures", e);
					}

					pictures.add(picture);
				}
			}
			
			login.setPictures(pictures);
		}								
	}
	
	private void populateDefaultPictureUrl(LoginResponse login, Map<String, String> responseMap) 
		throws LiveJournalServiceException {
		String defaultPictureUrl = responseMap.get("defaultpicurl");
		URL url;
		
		if(defaultPictureUrl != null) {
			try {
				url = new URL(defaultPictureUrl);
			} catch (MalformedURLException e) {
				throw new LiveJournalServiceException("Unable to populate picture url", e);
			}
			
			login.setDefaultPictureUrl(url);
		}
	}
	
	private void populateFastServer(LoginResponse login, Map<String, String> responseMap) {
		String fastServer = responseMap.get("fastserver");
		
		if(fastServer != null && fastServer.equalsIgnoreCase("1")) {
			login.setFastServer(true);
		}
	}

	private void buildGetEventPostParameters(GetEventParams params) {
		if(params.getTruncate() != 0)
			getPost().setParameter("truncate", new Integer(params.getTruncate()).toString());
		
		if(params.getSelectType() != null) {
			String selectType = null;
			
			if(params.getSelectType() == SelectType.LAST_N) {
				selectType = "lastn";
				getPost().setParameter("howmany", new Integer(params.getHowMany()).toString());
				if(params.getBeforeDate() != null) {
					getPost().setParameter(
							"beforedate", 
								LiveJournalUtils.toDateStringFromDate(params.getBeforeDate()));					
				}
			} else if(params.getSelectType() == SelectType.DAY) {
				selectType = "day";
				getPost().setParameter("year", getYearFromDate(params.getDayDate()));
				getPost().setParameter("month",getMonthFromDate(params.getDayDate()));
				getPost().setParameter("day", getDayFromDate(params.getDayDate()));
			} else if(params.getSelectType() == SelectType.ONE) {
				selectType = "one";
				getPost().setParameter("itemid", new Integer(params.getItemId()).toString());
			} else if(params.getSelectType() == SelectType.SYNCH_ITEMS) {
				selectType = "syncitems";
				getPost().setParameter(
						"lastsynch", 
						LiveJournalUtils.toDateStringFromDate(params.getLastSynchDate()));
			} else {
				assert(false);
			}
			
			getPost().setParameter("selecttype", selectType);			
		}
		
		if(params.isPreferSubject())
			getPost().setParameter("prefersubject", "1");
		
		if(params.isNoMetadataProperties())
			getPost().setParameter("noprops", "1");
		
		if(params.getUseJournal() != null) 
			getPost().setParameter("usejournal", params.getUseJournal());
		
	}
	
	private void buildEditEventPostParameters(TransformEventParams params) {
		assert(params.getItemId() != 0);
		getPost().setParameter("itemid", new Integer(params.getItemId()).toString());
		buildTransformEventPostParameters(params);
	}
	
	private void buildTransformEventPostParameters(TransformEventParams params) {
		getPost().setParameter("itemid", new Integer(params.getItemId()).toString());

		if(params.getAction() != null && params.getAction().equals(EventAction.DELETE)) 
			return;

		assert(params.getEvent() != null);
		getPost().setParameter("event", params.getEvent());
				
		assert(params.getLineEndings() != null);
		getPost().setParameter("lineendings", params.getLineEndings());
		
		assert(params.getSubject() != null);
		getPost().setParameter("subject", params.getSubject());
		
		// check for security type, if not there should default to public
		// there could be a bug here on Live Journal's end.
		if(params.getSecurity() != null) {
			if(params.getSecurity().equals(EventSecurity.PRIVATE))
				getPost().setParameter("security", "private");
			if(params.getSecurity().equals(EventSecurity.PUBLIC))
				getPost().setParameter("security", "public");
			if(params.getSecurity().equals(EventSecurity.USEMASK)) {
				getPost().setParameter("security", "usemask");
				getPost().setParameter("allowmask", params.getAllowmask().toString());
			}			
		}
			
		if(params.getMetadata() != null && params.getMetadata().getPropsMap() != null) {
			for(String name : params.getMetadata().getPropsMap().keySet()) {
				String propName = "prop_" + name;
				String value = params.getMetadata().getPropsMap().get(name);
				getPost().setParameter(propName, value);
			}
		}
		
		assert(params.getTimestamp() != null);
		getPost().setParameter("year", getYearFromDate(params.getTimestamp()));
		getPost().setParameter("mon",getMonthFromDate(params.getTimestamp()));
		getPost().setParameter("day", getDayFromDate(params.getTimestamp()));
		getPost().setParameter("hour", getHourFromDate(params.getTimestamp()));
		getPost().setParameter("min", getMinFromDate(params.getTimestamp()));
		
		buildPropertyParameters(params);
		
		if(params.getUseJournal() != null)
			getPost().setParameter("usejournal", params.getUseJournal());
	}
	
	private String getYearFromDate(Date date) {
		String year = LiveJournalUtils.toYearStringFromDate(date);		
		return year;
	}
	
	private String getMonthFromDate(Date date) {
		String month = LiveJournalUtils.toMonthStringFromDate(date);		
		return month;
	}
	
	private String getDayFromDate(Date date) {
		String day = LiveJournalUtils.toDayStringFromDate(date);		
		return day;
	}
	
	private String getHourFromDate(Date date) {
		String hour = LiveJournalUtils.toHourStringFromDate(date);		
		return hour;
	}
	
	private String getMinFromDate(Date date) {
		String min = LiveJournalUtils.toMinStringFromDate(date);		
		return min;
	}

	private void populateEventSecurity(Event event, String response) {
		if(response == null) {
			event.setSecurity(EventSecurity.PUBLIC);
		} else if (response.equalsIgnoreCase("private")) {
			event.setSecurity(EventSecurity.PRIVATE);
		} else {
			event.setSecurity(EventSecurity.USEMASK);
		}
	}
	
	private List<EventProperty> buildPropertiesList(Map<String, String> responseMap) {
		int propCount = NumberUtils.toInt(responseMap.get("prop_count"));
		List<EventProperty> props = new ArrayList<EventProperty>();
		
		for(int i = 1; i <= propCount; i++) {
			String idKey = "prop_" + i + "_itemid";
			String nameKey = "prop_" + i + "_name";
			String valueKey = "prop_" + i + "_value";
			
			EventProperty eventProp = new EventProperty();
			eventProp.setEventId(NumberUtils.toInt(responseMap.get(idKey)));
			eventProp.setName(responseMap.get(nameKey));
			eventProp.setValue(responseMap.get(valueKey));
			
			props.add(eventProp);
		}
		
		return props;
	}

	private void addPropertiesToEvents(List<Event> events, List<EventProperty> refProps) {
		for(Event event : events) {
			Map<String, String> props = new HashMap<String, String>();
			int eventId = event.getId();
			
			for(EventProperty prop : refProps) {
				if(prop.getEventId() == eventId) {
					props.put(prop.getName(), prop.getValue());
				}
			}
			
			event.setProperties(props);
		}
	}

	private void buildPropertyParameters(TransformEventParams params) {
		PostEventMetadata metadata = params.getMetadata();
		
		if(metadata != null) {
			if(metadata.getCurrentMood() != null)
				getPost().setParameter("current_mood", metadata.getCurrentMood());
			
			if(metadata.getCurrentMoodId() != 0) {
				getPost().setParameter("current_moodid", 
						new Integer(metadata.getCurrentMoodId()).toString());
			}
			
			if(metadata.getCurrentMusic() != null)
				getPost().setParameter("current_music", metadata.getCurrentMusic());
			
			if(metadata.isPreformatted())
				getPost().setParameter("opt_preformatted", "1");
			
			if(!metadata.isCommentsAllowed())
				getPost().setParameter("opt_notcomments", "1");
			
			if(metadata.getPictureKeyword() != null)
				getPost().setParameter("picture_keyword", metadata.getPictureKeyword());
			
			if(metadata.isBackdated())
				getPost().setParameter("opt_backdated", "1");
			
			if(metadata.isNoEmail())
				getPost().setParameter("opt_noemail", "1");
			
			if(metadata.getScreening() != null)
				getPost().setParameter("opt_screenting", metadata.getScreening());
			
			if(metadata.isUnknown8bit()) 
				getPost().setParameter("unkown8bit", "1");
			
			if(metadata.isHasScreened())
				getPost().setParameter("hasscreened", "1");
			
			if(metadata.getRevisionNum() != 0)
				getPost().setParameter("revnum", new Integer(metadata.getRevisionNum()).toString());
			
			if(metadata.getCommentAlterTime() != null) {
				getPost().setParameter("commentalter", 
						LiveJournalUtils.toDateStringFromDate(metadata.getCommentAlterTime()));
			}
			
			if(metadata.getSyndicationLink() != null)
				getPost().setParameter("syn_link", metadata.getSyndicationLink().toString());
			
			if(metadata.getRevisionTime() != null) {
				getPost().setParameter("revtime", 
						LiveJournalUtils.toDateStringFromDate(metadata.getRevisionTime()));
			}
			
			if(metadata.getRevisionNum() != 0) {
				getPost().setParameter("syn_id", new Integer(metadata.getRevisionNum()).toString());
			}			
		}
	}
	
}
