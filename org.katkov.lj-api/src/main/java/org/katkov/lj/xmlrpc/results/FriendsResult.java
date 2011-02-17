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

package org.katkov.lj.xmlrpc.results;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

public class FriendsResult {
    private Friend[] friends;
    private Friend[] friendOfs;
    private FriendGroup[] friendGroups;


    public FriendsResult(Map map) throws UnsupportedEncodingException {
        friendGroups = FriendGroup.createFriendGroups((Object[]) map.get("friendgroups"));
        friends = Friend.createFriends((Object[]) map.get("friends"));
        friendOfs = Friend.createFriends((Object[]) map.get("friendOfs"));
    }


    public Friend[] getFriends() {
        return friends;
    }

    public Friend[] getFriendOfs() {
        return friendOfs;
    }

    public FriendGroup[] getFriendGroups() {
        return friendGroups;
    }


    public String toString() {
        return "FriendsResult{" +
                "friends=" + (friends == null ? null : Arrays.asList(friends)) +
                ", friendOfs=" + (friendOfs == null ? null : Arrays.asList(friendOfs)) +
                ", friendGroups=" + (friendGroups == null ? null : Arrays.asList(friendGroups)) +
                '}';
    }
}
