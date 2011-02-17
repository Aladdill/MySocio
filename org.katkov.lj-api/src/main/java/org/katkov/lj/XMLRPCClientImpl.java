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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.client.TimingOutCallback;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.katkov.lj.xmlrpc.arguments.*;
import org.katkov.lj.xmlrpc.results.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Default implementation of XMLRPCClient interface
 */

public class XMLRPCClientImpl implements XMLRPCClient {
    private Log logger = LogFactory.getLog(XMLRPCClient.class);

    private XmlRpcClient client;
    private static String HTTP_WWW_LIVEJOURNAL_COM_INTERFACE_XMLRPC = "http://www.livejournal.com/interface/xmlrpc";


    XMLRPCClientImpl() {
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(HTTP_WWW_LIVEJOURNAL_COM_INTERFACE_XMLRPC));
            client = new XmlRpcClient();
            client.setConfig(config);
            client.setTypeFactory(new LJTypeFactory(client));
            logger.debug("XmlRpcClientConfigImpl was configured");
        } catch (MalformedURLException e) {
            new RuntimeException("Failed to create server URL:" + HTTP_WWW_LIVEJOURNAL_COM_INTERFACE_XMLRPC, e);
        }
    }

    public BlogEntry[] getevents(GetEventsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering getevents(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        List<BlogEntry> result = new ArrayList<BlogEntry>();
        try {
            Map eventsMap;
            eventsMap = (Map) doXmlRpcCall("LJ.XMLRPC.getevents", new Object[]{argument}, timeout);
            Object[] array = (Object[]) eventsMap.get("events");

            for (Object anArray : array) {
                Map map = (Map) anArray;
                BlogEntry entry = new BlogEntry(map);
                result.add(entry);
            }
            BlogEntry[] entries = result.toArray(new BlogEntry[result.size()]);
            logger.debug("Exiting getevents");
            return entries;
        } catch (TimeoutException e) {
            throw new LJTimeoutException(e);
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public FriendCheckResult checkfriends(CheckFriendsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering checkfriends(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.checkfriends", new Object[]{argument}, timeout);
            FriendCheckResult checkResult = new FriendCheckResult(map);
            logger.debug("Exiting checkfriends");
            return checkResult;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public UserData login(BaseArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering login(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map userDataMap = (Map) doXmlRpcCall("LJ.XMLRPC.login", new Object[]{argument}, timeout);
            UserData userData = new UserData(userDataMap);
            logger.debug("Exiting login");
            return userData;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public ConsoleCommandResult consolecommand(ConsoleCommandArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering consolecommand(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.consolecommand", new Object[]{argument}, timeout);
            Map resultMap = (Map) ((Object[]) map.get("results"))[0];
            ConsoleCommandResult consoleCommandResult = new ConsoleCommandResult(resultMap);
            logger.debug("Exiting consolecommand");
            return consoleCommandResult;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public int editevent(EditEventArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering editevent(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.editevent", new Object[]{argument}, timeout);
            Integer integer = (Integer) map.get("itemid");
            logger.debug("Exiting editevent");
            return integer;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public DayCount[] getdaycounts(GetDayCountsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering getdaycounts(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        List<DayCount> result = new ArrayList<DayCount>();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.getdaycounts", new Object[]{argument}, timeout);
            for (Object object : (Object[]) map.get("daycounts")) {
                result.add(new DayCount((Map) object));
            }
            DayCount[] dayCounts = result.toArray(new DayCount[result.size()]);
            logger.debug("Exiting getdaycounts");
            return dayCounts;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }


    public FriendsResult getfriends(GetFriendsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering getfriends(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.getfriends", new Object[]{argument}, timeout);
            FriendsResult friendsResult = new FriendsResult(map);
            logger.debug("Exiting getfriends");
            return friendsResult;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public FriendGroup[] getfriendgroups(BaseArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering getfriendgroups(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.getfriendgroups", new Object[]{argument}, timeout);
            FriendGroup[] friendGroups = FriendGroup.createFriendGroups((Object[]) map.get("friendgroups"));
            logger.debug("Exiting getfriendgroups");
            return friendGroups;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public PostResult postevent(PostEventArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering postevent(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.postevent", new Object[]{argument}, timeout);
            PostResult postResult = new PostResult(map);
            logger.debug("Exiting postevent");
            return postResult;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public String sessiongenerate(SessionGenerateArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering sessiongenerate(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.sessiongenerate", new Object[]{argument}, timeout);
            String s = (String) map.get("ljsession");
            logger.debug("Exiting sessiongenerate");
            return s;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public void sessionexpire(SessionExpireArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering sessionexpire(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            doXmlRpcCall("LJ.XMLRPC.sessionexpire", new Object[]{argument}, timeout);
            logger.debug("Exiting sessionexpire");
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public SyncResult syncitems(SyncItemsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering syncitems(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.syncitems", new Object[]{argument}, timeout);
            SyncResult syncResult = new SyncResult(map);
            logger.debug("Exiting syncitems");
            return syncResult;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public void editfriends(EditFriendsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering editfriends(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            doXmlRpcCall("LJ.XMLRPC.editfriends", new Object[]{argument}, timeout);
            logger.debug("Exiting editfriends");
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public void editfriendgroups(EditFriendGroupsArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering editfriendgroups(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            doXmlRpcCall("LJ.XMLRPC.editfriendgroups", new Object[]{argument}, timeout);
            logger.debug("Exiting editfriendgroups");
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public Friend[] friendof(GetFriendOfArgument argument, int timeout) throws LJRuntimeException {
        logger.debug("Entering friendof(" + argument + ", " + timeout + ")");
        argument.stripNullValues();
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.friendof", new Object[]{argument}, timeout);
            Friend[] friends = Friend.createFriends((Object[]) map.get("friendofs"));
            logger.debug("Exiting friendof");
            return friends;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    public Challenge getchallenge(int timeout) throws LJRuntimeException {
        logger.debug("Entering getchallenge(" + timeout + ")");
        try {
            Map map = (Map) doXmlRpcCall("LJ.XMLRPC.getchallenge", new Object[]{}, timeout);
            Challenge challenge = new Challenge(map);
            logger.debug("Exiting getchallenge");
            return challenge;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    private Object doXmlRpcCall(String methodName, Object[] arguments, long timeout) throws Throwable {
        if (timeout != 0) {
            logger.debug("Permorming XML-RPC call to: " + methodName + " with timeout setting:" + timeout);
            TimingOutCallback callback = new TimingOutCallback(timeout);
            client.executeAsync(methodName, arguments, callback);
            return callback.waitForResponse();
        } else {
            logger.debug("Permorming XML-RPC call to: " + methodName + " without timeout setting");
            return client.execute(methodName, arguments);
        }
    }


}
