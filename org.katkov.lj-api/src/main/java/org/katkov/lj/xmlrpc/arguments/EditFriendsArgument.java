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

package org.katkov.lj.xmlrpc.arguments;

import java.util.HashMap;

public class EditFriendsArgument extends BaseArgument {


    /**
     * @param userNames A list of variable of this form removes the friend users from the user's friend list. It is not an error to delete an already non-existant friend. The value should just be 1. Containing items:
     * @@optional
     */
    public void delete(String[] userNames) {
        struct.put("delete", userNames);
    }

    /**
     * @param friends To add friends, send a variable list of this form.
     * @@optional
     */
    public void add(NewFriend[] friends) {
        struct.put("add", friends);
    }

    public static class NewFriend extends HashMap {

        public NewFriend() {
        }

        public NewFriend(String username, String fgcolor, String bgcolor, Integer groupmask) {
            put("username", username);
            put("fgcolor", fgcolor);
            put("bgcolor", bgcolor);
            put("groupmask", groupmask);
        }

        /**
         * Username of user logging in.
         *
         * @param username name
         */
        public void setUsername(String username) {
            if (username != null)
                put("username", username);
        }

        /**
         * Sets the text color of the friend being added. This value is a HTML-style hex-triplet, and must either be of
         * the form #rrggbb or not sent at all. By default, the value assumed is #000000, black.
         *
         * @param fgcolor color
         */
        public void setFgcolor(String fgcolor) {
            if (fgcolor != null)
                put("fgcolor", fgcolor);
        }

        /**
         * Sets the background color of the friend being added. This value is a HTML-style hex-triplet, and must either
         * be of the form #rrggbb or not sent at all. By default, the value assumed is #FFFFFF, white.
         *
         * @param bgcolor color
         */
        public void setBgcolor(String bgcolor) {
            if (bgcolor != null)
                put("bgcolor", bgcolor);
        }

        /**
         * Sets this user's groupmask. Only use this in clients if you've very recently loaded the friend groups. If
         * your client has been loaded on the end user's desktop for days and you haven't loaded friend groups since it
         * started, they may be inaccurate if they've modified their friend groups through the website or another
         * client. In general, don't use this key unless you know what you're doing.
         *
         * @param groupmask mask
         */
        public void setGroupmask(Integer groupmask) {
            if (groupmask != null)
                put("groupmask", groupmask);
        }
    }
}
