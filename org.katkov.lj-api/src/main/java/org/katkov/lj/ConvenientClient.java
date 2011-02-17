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

import org.katkov.lj.comments.Comment;
import org.katkov.lj.comments.CommentsMetaData;
import org.katkov.lj.http.UserProfile;
import org.katkov.lj.xmlrpc.SecurityType;
import org.katkov.lj.xmlrpc.arguments.*;
import org.katkov.lj.xmlrpc.results.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Wrapper on top on low-level interfaces. Provides consistant and convenient way to call livejournal services.
 */
public class ConvenientClient {
    private XMLRPCClient client;
    private CommentsClient commentsClient;
    private HTTPClient httpClient;
    private String login;
    private String password;
    private boolean loggedIn;
    private String authCookie;
    private Comment[] comments;


    public ConvenientClient(XMLRPCClient client, CommentsClient commentsClient, HTTPClient httpClient) {
        this.client = client;
        this.commentsClient = commentsClient;
        this.httpClient = httpClient;
    }

    /**
     * Performs all required authentication procedures
     *
     * @param login    livejounral user name to be logged in
     * @param password password
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return userdata struct
     * @see org.katkov.lj.xmlrpc.results.UserData
     */
    public UserData login(String login, String password, int timeout) {
        this.login = login;
        this.password = password;
        LoginArgument argument = new LoginArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        UserData userData = client.login(argument, timeout);
        loggedIn = true;
        authCookie = getAuthCookie(timeout);
        return userData;
    }

    /**
     * Retrieves all blog entries from the day one up until now
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return an array of blog entries
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     * @see org.katkov.lj.LJPartialResultsRuntimeException
     */
    public BlogEntry[] getAllBlogEntries(int timeout) {
        return getBlogEntries(null, null, timeout);
    }

    /**
     * Retrieves all blog entries published before the given date
     *
     * @param date    boudary date
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return an array of blog entries
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     */
    public BlogEntry[] getBlogEntriesBefore(Date date, int timeout) {
        return getBlogEntries(null, date, timeout);
    }


    /**
     * Retrieves all blog entries published after the given date
     *
     * @param date    boudary date
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return an array of blog entries
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     */
    public BlogEntry[] getBlogEntriesAfter(Date date, int timeout) {
        return getBlogEntries(date, null, timeout);
    }

    /**
     * Retrieves all blog entries published on the given date
     *
     * @param date    certain date
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return an array of blog entries
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     */
    public BlogEntry[] getBlogEntriesOn(Date date, int timeout) {
        assertLoggedIn();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        GetEventsArgument argument = new GetEventsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.setSelecttype(GetEventsArgument.Type.DAY);
        argument.setYear(calendar.get(Calendar.YEAR));
        argument.setMonth(calendar.get(Calendar.MONTH) + 1);
        argument.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        return client.getevents(argument, timeout);
    }

    /**
     * Retrieves all blog entries published during the given time frame
     *
     * @param dateFrom boudary date from
     * @param dateTo   boudary date to
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return an array of blog entries
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     */
    public BlogEntry[] getBlogEntries(Date dateFrom, Date dateTo, int timeout) {
        long startTime = System.currentTimeMillis();
        DayCount[] dayCounts = getDayCounts(timeout);

        List<BlogEntry> result = new ArrayList<BlogEntry>();
        for (DayCount dayCount : dayCounts) {
            boolean checkFrom = true;
            if (dateFrom != null) {
                checkFrom = dayCount.getDate().after(dateFrom);
            }
            boolean checkTo = true;
            if (dateTo != null) {
                checkTo = dayCount.getDate().before(dateTo);
            }
            int timeoutValue;
            if (timeout == 0) {
                timeoutValue = 0;
            } else {
                timeoutValue = timeout - (int) (System.currentTimeMillis() - startTime);
            }
            if (timeoutValue < 0) {
                throw new LJTimeoutException("Time has expire");
            }

            if (checkFrom && checkTo) {
                result.addAll(Arrays.asList(getBlogEntriesOn(dayCount.getDate(), timeoutValue)));
            }
        }
        return result.toArray(new BlogEntry[result.size()]);
    }

    /**
     * Retrieves blog entry by unique id
     *
     * @param itemId  id
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return BlogEntry
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     */
    public BlogEntry getBlogEntry(int itemId, int timeout) {
        assertLoggedIn();

        GetEventsArgument argument = new GetEventsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.setSelecttype(GetEventsArgument.Type.ONE);
        argument.setItemid(itemId);
        return client.getevents(argument, timeout)[0];
    }

    /**
     * Retrieves N recent blogentries
     *
     * @param howmany limit
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return an array of blog entries
     * @see org.katkov.lj.xmlrpc.results.BlogEntry
     */
    public BlogEntry[] getMostRecentBlogEntries(int howmany, int timeout) {
        assertLoggedIn();

        GetEventsArgument argument = new GetEventsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.setSelecttype(GetEventsArgument.Type.LASTN);
        argument.setHowmany(howmany);
        return client.getevents(argument, timeout);

    }

    /**
     * Retrieves the number of journal entries per day.
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return DayCount struct
     * @see org.katkov.lj.xmlrpc.results.DayCount
     */
    public DayCount[] getDayCounts(int timeout) {
        assertLoggedIn();
        GetDayCountsArgument argument = new GetDayCountsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        return client.getdaycounts(argument, timeout);
    }

    /**
     * Generate a session cookie.
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return cookie
     */
    public String getAuthCookie(int timeout) {
        assertLoggedIn();
        SessionGenerateArgument argument = new SessionGenerateArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        return client.sessiongenerate(argument, timeout);
    }

    /**
     * Retrieves all comments from the day one
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return array of comments
     * @see org.katkov.lj.comments.Comment
     */
    public Comment[] getAllComments(int timeout) {
        long startTime = System.currentTimeMillis();
        CommentsMetaData metaData = commentsClient.getCommentsMetaData(authCookie, timeout);
        int timeoutValue;
        if (timeout == 0) {
            timeoutValue = 0;
        } else {
            timeoutValue = timeout - (int) (System.currentTimeMillis() - startTime);
        }
        comments = commentsClient.getComments(authCookie, metaData, timeoutValue);
        return comments;
    }

    /**
     * Retrieves all comments posted on the given blog entry
     *
     * @param blogEntryID id
     * @param timeout     timeout in millisecs, a value of zero  means the timeout is not used.
     * @return array of comments
     * @see org.katkov.lj.comments.Comment
     */
    public Comment[] getCommentsOn(int blogEntryID, int timeout) {
        long startTime = System.currentTimeMillis();
        List<Comment> result = new ArrayList<Comment>();
        if (comments == null) {
            CommentsMetaData metaData = commentsClient.getCommentsMetaData(authCookie, timeout);
            int timeoutValue;
            if (timeout == 0) {
                timeoutValue = 0;
            } else {
                timeoutValue = timeout - (int) (System.currentTimeMillis() - startTime);
            }
            comments = commentsClient.getComments(authCookie, metaData, timeoutValue);
        }
        for (Comment comment : comments) {
            if (comment.getJitemid() == blogEntryID) {
                result.add(comment);
            }
        }
        return result.toArray(new Comment[result.size()]);
    }

    /**
     * Updates a blog entry in a specified blog
     *
     * @param journal      Journal to publish this blog entry
     * @param itemId       The unique integer ItemID of the blog entry
     * @param body         The event text itself. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are
     *                     removed internally to make everything into Unix-style line-endings (just \ns). Posts may also
     *                     contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s
     *                     when displaying them, so your client should not try to insert these itself.
     * @param subject      The subject for this post. Limited to 255 characters. No newlines.
     * @param date         The time the blog entry
     * @param securityType Specifies who can read this post. Valid values are public (default), private and usemask.
     *                     When value is usemask, viewability is controlled by the allowmask.
     * @param allowmask    Relevant when security is usemask. A 32-bit unsigned integer representing which of the user's
     *                     groups of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to
     *                     read it. Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it.
     *                     Bit 31 is reserved.
     * @param timeout      timeout in millisecs, a value of zero  means the timeout is not used.
     */

    public void updateBlogEntry(String journal, int itemId, Date date, String body, String subject, SecurityType securityType, Integer allowmask, int timeout) {
        EditEventArgument argument = new EditEventArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.setItemId(itemId);
        argument.setEvent(body);
        argument.setSubject(subject);
        argument.setSecurity(securityType);
        argument.setAllowmask(allowmask);
        argument.setUsejournal(journal);

        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            argument.setYear(calendar.get(Calendar.YEAR));
            argument.setMon(calendar.get(Calendar.MONTH) + 1);
            argument.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            argument.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            argument.setMin(calendar.get(Calendar.MINUTE));
        }
        client.editevent(argument, timeout);
    }

    /**
     * Updates a blog entry in user's blog
     *
     * @param itemId       The unique integer ItemID of the blog entry
     * @param body         The event text itself. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are
     *                     removed internally to make everything into Unix-style line-endings (just \ns). Posts may also
     *                     contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s
     *                     when displaying them, so your client should not try to insert these itself.
     * @param subject      The subject for this post. Limited to 255 characters. No newlines.
     * @param date         The time the blog entry
     * @param securityType Specifies who can read this post. Valid values are public (default), private and usemask.
     *                     When value is usemask, viewability is controlled by the allowmask.
     * @param allowmask    Relevant when security is usemask. A 32-bit unsigned integer representing which of the user's
     *                     groups of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to
     *                     read it. Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it.
     *                     Bit 31 is reserved.
     * @param timeout      timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void updateBlogEntry(int itemId, Date date, String body, String subject, SecurityType securityType, Integer allowmask, int timeout) {
        this.updateBlogEntry(login, itemId, date, body, subject, securityType, allowmask, timeout);
    }

    /**
     * Updates a blog entry in user's blog
     *
     * @param itemId  The unique integer ItemID of the blog entry
     * @param body    The event text itself. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are
     *                removed internally to make everything into Unix-style line-endings (just \ns). Posts may also
     *                contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s
     *                when displaying them, so your client should not try to insert these itself.
     * @param subject The subject for this post. Limited to 255 characters. No newlines.
     * @param date    The time the blog entry
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void updateBlogEntry(int itemId, Date date, String body, String subject, int timeout) {
        this.updateBlogEntry(login, itemId, date, body, subject, null, null, timeout);
    }

    /* 
     * Publishes a new blog entry to a specified blog
     * @param journal      Journal to publish this blog entry
     * @param date         The time the blog entry
     * @param body         The event text itself. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are
     *                     removed internally to make everything into Unix-style line-endings (just \ns). Posts may also
     *                     contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s
     *                     when displaying them, so your client should not try to insert these itself.
     * @param subject      The subject for this post. Limited to 255 characters. No newlines.
     * @param securityType Specifies who can read this post. Valid values are public (default), private and usemask.
     *                     When value is usemask, viewability is controlled by the allowmask.
     * @param allowmask    Relevant when security is usemask. A 32-bit unsigned integer representing which of the user's
     *                     groups of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to
     *                     read it. Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it.
     *                     Bit 31 is reserved.
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void addBlogEntry(String journal, Date date, String body, String subject, SecurityType securityType, Integer allowmask, int timeout) {
        PostEventArgument argument = new PostEventArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.setEvent(body);
        argument.setSubject(subject);
        argument.setSecurity(securityType);
        argument.setAllowmask(allowmask);
        argument.setUsejournal(journal);

        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            argument.setYear(calendar.get(Calendar.YEAR));
            argument.setMon(calendar.get(Calendar.MONTH) + 1);
            argument.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            argument.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            argument.setMin(calendar.get(Calendar.MINUTE));
        }
        client.postevent(argument, timeout);
    }

    /* 
     * Publishes a new blog entry to the user's blog
     * @param date         The time the blog entry
     * @param body         The event text itself. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are
     *                     removed internally to make everything into Unix-style line-endings (just \ns). Posts may also
     *                     contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s
     *                     when displaying them, so your client should not try to insert these itself.
     * @param subject      The subject for this post. Limited to 255 characters. No newlines.
     * @param securityType Specifies who can read this post. Valid values are public (default), private and usemask.
     *                     When value is usemask, viewability is controlled by the allowmask.
     * @param allowmask    Relevant when security is usemask. A 32-bit unsigned integer representing which of the user's
     *                     groups of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to
     *                     read it. Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it.
     *                     Bit 31 is reserved.
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used. 
     */
    public void addBlogEntry(Date date, String body, String subject, SecurityType securityType, Integer allowmask, int timeout) {
        this.addBlogEntry(login, date, body, subject, securityType, allowmask, timeout);
    }

    /* 
    * Publishes a new blog entry to the user's blog
    * @param date         The time the blog entry
    * @param body         The event text itself. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are
    *                     removed internally to make everything into Unix-style line-endings (just \ns). Posts may also
    *                     contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s
    *                     when displaying them, so your client should not try to insert these itself.
    * @param subject      The subject for this post. Limited to 255 characters. No newlines.
    * @param timeout timeout in millisecs, a value of zero  means the timeout is not used. 
    */
    public void addBlogEntry(Date date, String body, String subject, int timeout) {
        this.addBlogEntry(login, date, body, subject, null, null, timeout);
    }

    /**
     * Deletes blog entry with given ID
     *
     * @param itemid  blog entry id
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void deleteBlogEntry(int itemid, int timeout) {
        this.updateBlogEntry(itemid, null, null, null, timeout);
    }

    /**
     * Adds a friend
     *
     * @param name            livejournal login of the friend being added
     * @param foregroundColor foreground color text color of the friend being added
     * @param backgroundColor background the background color of the friend being added.
     * @param mask            this user's groupmask. Only use this in clients if you've very recently loaded the friend groups. If
     *                        your client has been loaded on the end user's desktop for days and you haven't loaded friend groups since it
     *                        started, they may be inaccurate if they've modified their friend groups through the website or another
     *                        client. In general, don't use this key unless you know what you're doing.
     * @param timeout         timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void addFriend(String name, Color foregroundColor, Color backgroundColor, Integer mask, int timeout) {
        EditFriendsArgument argument = new EditFriendsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        EditFriendsArgument.NewFriend friend = new EditFriendsArgument.NewFriend();
        friend.setUsername(name);
        if (foregroundColor != null) {
            friend.setFgcolor(LJHelpers.RGBtoHTML(foregroundColor.getRGB()));
        }
        if (backgroundColor != null) {
            friend.setBgcolor(LJHelpers.RGBtoHTML(backgroundColor.getRGB()));
        }
        friend.setGroupmask(mask);
        argument.add(new EditFriendsArgument.NewFriend[]{friend});
        client.editfriends(argument, timeout);
    }

    /**
     * Adds a friend
     *
     * @param name    livejournal login of the friend being added
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void addFriend(String name, int timeout) {
        this.addFriend(name, null, null, null, timeout);
    }

    /**
     * Removes a friend
     *
     * @param name    livejournal login of the friend being removed
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     */
    public void removeFriends(String name, int timeout) {
        EditFriendsArgument argument = new EditFriendsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.delete(new String[]{name});
        client.editfriends(argument, timeout);
    }

    /**
     * Returns a list of other LiveJournal users which list this user as their friend.
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return array of other LiveJournal users which list this user as their friend
     */
    public Friend[] getFriendOf(int timeout) {
        GetFriendOfArgument argument = new GetFriendOfArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        return client.friendof(argument, timeout);
    }

    /**
     * Returns a list of which other LiveJournal users this user lists as their friend.
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return array of ther LiveJournal users which this user lists as their friend
     */
    public Friend[] getFriends(int timeout) {
        GetFriendsArgument argument = new GetFriendsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        return client.getfriends(argument, timeout).getFriends();
    }

    /**
     * Returns complete friends information for the user
     *
     * @param timeout timeout in millisecs, a value of zero  means the timeout is not used.
     * @return FriedResult structure
     */
    public FriendsResult getFriendsInfo(int timeout) {
        GetFriendsArgument argument = new GetFriendsArgument();
        argument.setUsername(login);
        argument.setHpassword(password);
        argument.setIncludeFriendOf(true);
        argument.setIncludeFriendOf(true);
        return client.getfriends(argument, timeout);
    }

    /**
     * Tries to parse HTML page fetched from at http://<username>.livejournal.com/profile?mode=full
     * Please notice that this implementation is unreliable as livejournal.com can change HTML pages
     * at any time
     *
     * @param userName user names
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return UserProfiles
     */
    public UserProfile getOtherUserInfo(String userName, int timeout) {
        return httpClient.getUserProfiles(userName, timeout);
    }

    /**
     * Tries to parse HTML page
     * Please notice that this implementation is unreliable as livejournal.com can change HTML pages
     * at any time
     *
     * @param userName community name to process
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used.
     * @return UserProfiles
     */
    public UserProfile getCommunityInfo(String userName, int timeout) {
        return httpClient.getCommunityProfiles(userName, timeout);
    }


    private void assertLoggedIn() {
        if (!loggedIn) {
            throw new IllegalStateException("Call login(...) first");
        }
    }
}

    