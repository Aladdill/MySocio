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

import java.util.Arrays;
import java.util.Map;

/**
 * Extended user info
 */
public class UserData {

    private String fullname;
    private String message;
    private FriendGroup[] friendgroups;
    private String[] usejournals;
    private Mood[] moods;
    private String[] pickws;
    private String[] pickwurls;
    private String defaultpicurl;
    private Boolean fastserver;
    private Menu[] menus;


    public UserData(Map map) {
        fullname = map.get("fullname").toString();
        message = (String) map.get("message");
        friendgroups = FriendGroup.createFriendGroups((Object[]) map.get("friendgroups"));
        usejournals = createStringArray((Object[]) map.get("usejournals"));
        moods = createMoods((Object[]) map.get("moods"));
        pickws = createStringArray((Object[]) map.get("pickws"));
        pickwurls = createStringArray((Object[]) map.get("pickwurls"));
        defaultpicurl = (String) map.get("defaultpicurl");
        fastserver = map.get("fastserver") != null && (Integer) map.get("fastserver") > 0;
        menus = createMenus((Object[]) map.get("menus"));
    }

    private static Menu[] createMenus(Object[] objects) {
        if (objects == null) {
            return null;
        }
        Menu[] result = new Menu[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Menu((Map) objects[i]);
        }
        return result;
    }

    private static String[] createStringArray(Object[] objects) {
        if (objects == null) {
            return null;
        }
        String[] result = new String[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (String) objects[i];
        }
        return result;
    }

    private static Mood[] createMoods(Object[] objects) {
        if (objects == null) {
            return null;
        }
        Mood[] result = new Mood[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Mood((Map) objects[i]);
        }
        return result;
    }

    public String getFullname() {
        return fullname;
    }

    public String getMessage() {
        return message;
    }

    public FriendGroup[] getFriendgroups() {
        return friendgroups;
    }

    public String[] getUsejournals() {
        return usejournals;
    }

    public Mood[] getMoods() {
        return moods;
    }

    public String[] getPickws() {
        return pickws;
    }

    public String[] getPickwurls() {
        return pickwurls;
    }

    public String getDefaultpicurl() {
        return defaultpicurl;
    }

    public Boolean getFastserver() {
        return fastserver;
    }

    public Menu[] getMenus() {
        return menus;
    }


    public String toString() {
        return "UserData{" +
                "fullname='" + fullname + '\'' +
                ", message='" + message + '\'' +
                ", friendgroups=" + (friendgroups == null ? null : Arrays.asList(friendgroups)) +
                ", usejournals=" + (usejournals == null ? null : Arrays.asList(usejournals)) +
                ", moods=" + (moods == null ? null : Arrays.asList(moods)) +
                ", pickws=" + (pickws == null ? null : Arrays.asList(pickws)) +
                ", pickwurls=" + (pickwurls == null ? null : Arrays.asList(pickwurls)) +
                ", defaultpicurl='" + defaultpicurl + '\'' +
                ", fastserver=" + fastserver +
                ", menus=" + (menus == null ? null : Arrays.asList(menus)) +
                '}';
    }
}
