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

import org.katkov.lj.LJHelpers;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Friend {
    private String username;
    private String fullname;
    private String type;
    private String fgcolor;
    private String bgcolor;
    private Integer groupmask;

    public Friend(Map map) throws UnsupportedEncodingException {
        username = (String) map.get("username");
        fullname = LJHelpers.getUnicodeText(map.get("fullname"));
        type = (String) map.get("type");
        fgcolor = (String) map.get("fgcolor");
        bgcolor = (String) map.get("bgcolor");
        groupmask = (Integer) map.get("groupmask");
    }


    public Friend(String username, String fgcolor, String bgcolor, Integer groupmask) {
        this.username = username;
        this.fgcolor = fgcolor;
        this.bgcolor = bgcolor;
        this.groupmask = groupmask;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getType() {
        return type;
    }

    public String getFgcolor() {
        return fgcolor;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public int getGroupmask() {
    	if (groupmask == null){
    		return 0;
    	}
        return groupmask;
    }


    public String toString() {
        return "Friend{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", type='" + type + '\'' +
                ", fgcolor='" + fgcolor + '\'' +
                ", bgcolor='" + bgcolor + '\'' +
                ", groupmask=" + groupmask +
                '}';
    }

    public static Friend[] createFriends(Object[] objects) throws UnsupportedEncodingException {
        if (objects == null) {
            return null;
        }
        Friend[] result = new Friend[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Friend((Map) objects[i]);
        }
        return result;

    }
}
