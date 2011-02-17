/*
 * Copyright (c) 2006, Igor Katkov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided 
 * that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *       and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *       and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * The name of the author may not be used may not be used to endorse or 
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.katkov.lj;

import org.katkov.lj.xmlrpc.arguments.*;
import org.katkov.lj.xmlrpc.results.*;

/**
 * This the XML-RPC version of the Client/Server protocol.
 * XML-RPC allows programs to make procedure calls over the internet, regardless of differing operating systems and
 * environments. It's also handy for returning preconstructed libraries of information (instead of having to have your
 * program parse the regular server feedback).
 * <p/>
 * All methods throw LJException runtime exceptions, which represent XML-RPC errors and server side problems.
 * <br/>
 * Reasoning: Some cases occur where specific types of exceptions might usefully be caught and handled, but
 * only a minority of callers would want to handle the problem themselves.
 * Rather than make the rest of the classes pay for possibility in the form of catching and rethrowing these
 * exceptions, unchecked exceptions are used.
 */
public interface XMLRPCClient {

    /**
     * Checks to see if your friends list has been updated since a specified time.
     * <p/>
     * Mode that clients can use to poll the server to see if their friends list has been updated.
     * This request is extremely quick, and is the preferred way for users to see when their friends list
     * is updated, rather than pounding on reload in their browser, which is stressful on the serves.
     *
     * @param argument CheckFriendsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return FriendCheckResult
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public FriendCheckResult checkfriends(CheckFriendsArgument argument, int timeout);

    /**
     * Run an administrative command.
     * <p/>
     * The LiveJournal server has a text-based shell-like admininistration console where less-often used commands
     * can be entered. There's a web interface to this shell online, and this is another gateway to that.
     *
     * @param argument ConsoleCommandArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return ConsoleCommandResult
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public ConsoleCommandResult consolecommand(ConsoleCommandArgument argument, int timeout);


    /**
     * Edit or delete a user's past journal entry
     * <p/>
     * Modify an already created event. If fields are empty, it will delete the event.
     *
     * @param argument EditEventArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return int event ID
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public int editevent(EditEventArgument argument, int timeout);


    /**
     * Edit the user's defined groups of friends.
     * <p/>
     * Given several optional lists, will add/delete/update/rename the friends groups for a user.
     *
     * @param argument EditFriendGroupsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public void editfriendgroups(EditFriendGroupsArgument argument, int timeout);

    /**
     * Add, edit, or delete friends from the user's friends list.
     * <p/>
     * Takes up to two lists, one of friends to delete and one of friends to add. Several options are allowed to be
     * specified when adding a friend.
     *
     * @param argument EditFriendsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public void editfriends(EditFriendsArgument argument, int timeout);


    /**
     * Returns a list of other LiveJournal users which list this user as their friend.
     * <p/>
     * Returns a "friends of" list for a specified user. An optional limit of returned friends can be supplied.
     *
     * @param argument GetFriendOfArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return Friend[] array of other LiveJournal users which list this user as their friend
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public Friend[] friendof(GetFriendOfArgument argument, int timeout);

    /**
     * Generate a server challenge string for authentication.
     * <p/>
     * Generate a one-time, quick expiration challenge to be used in challenge/response authentication methods.
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return Challenge
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public Challenge getchallenge(int timeout);

    /**
     * Retrieves the number of journal entries per day.
     * <p/>
     * This mode retrieves the number of journal entries per day. Useful for populating calendar widgets in GUI clients.
     * Optionally a journal can be specified. It returns a list of the dates and accompanied counts.
     *
     * @param argument GetDayCountsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return DayCount[]
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public DayCount[] getdaycounts(GetDayCountsArgument argument, int timeout);

    /**
     * Download parts of the user's journal.
     * <p/>
     * Given a set of specifications, will return a segment of entries up to a limit set by the server.
     * Has a set of options for less, extra, or special data to be returned.
     *
     * @param argument GetEventsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return BlogEntry[]
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public BlogEntry[] getevents(GetEventsArgument argument, int timeout);

    /**
     * Returns a list of which other LiveJournal users this user lists as their friend.
     * <p/>
     * Returns a verbose list of information on friends a user has listed. Optionally able to include their friends of
     * list, the friends group associated with each user, and a limit on the number of friends to return.
     *
     * @param argument GetFriendsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return FriendsResult
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public FriendsResult getfriends(GetFriendsArgument argument, int timeout);

    /**
     * Retrieves a list of the user's defined groups of friends.
     *
     * @param argument BaseArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return FriendGroup[]
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public FriendGroup[] getfriendgroups(BaseArgument argument, int timeout);

    /**
     * Validate user's password and get base information needed for client to function
     * <p/>
     * Login to the server, while announcing your client version. The server returns with whether the password is good
     * or not, the user's name, an optional message to be displayed to the user, the list of the user's friend groups,
     * and other things.
     *
     * @param argument BaseArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return UserData
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public UserData login(BaseArgument argument, int timeout);

    /**
     * The most important mode, this is how a user actually submits a new log entry to the server.
     * <p/>
     * Given all of the require information on a post, optioanlly adding security or meta data, will create a new entry.
     * Will return the itemid of the new post.
     *
     * @param argument PostEventArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return PostResult
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public PostResult postevent(PostEventArgument argument, int timeout);

    /**
     * Expires session cookies.
     * <p/>
     * Using this request mode, you can expire previously generated sessions, whether you generated them using the
     * sessiongenerate call or the user logged in on the web site.
     *
     * @param argument SessionExpireArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public void sessionexpire(SessionExpireArgument argument, int timeout);

    /**
     * Generate a session cookie.
     * <p/>
     * In order to interact with some web based parts of the site, such as the comment exporter, it is often useful
     * to be able to generate a login cookie without having to actually post login information to the login.bml page.
     * This mode will, with proper authentication, provide you with a session cookie to use for authentication purposes.
     *
     * @param argument SessionGenerateArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return String a session cookie
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public String sessiongenerate(SessionGenerateArgument argument, int timeout);


    /**
     * Returns a list of all the items that have been created or updated for a user.
     * <p/>
     * Returns a list (or part of a list) of all the items (journal entries, to-do items, comments) that have been
     * created or updated on LiveJournal since you last downloaded them. Note that the items themselves are not
     * returned --- only the item type and the item number. After you get this you have to go fetch the items using
     * another protocol mode. For journal entries (type "L"), use the getevents mode with a selecttype of "syncitems".
     *
     * @param argument SyncItemsArgument
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return SyncResult
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     */
    public SyncResult syncitems(SyncItemsArgument argument, int timeout);
}

