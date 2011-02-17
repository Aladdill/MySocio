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

package org.katkov.lj.http;

import java.util.Date;

public class UserProfile {
    private long id;
    private String name;
    private String location;
    private String website;
    private String blog;
    private String userpic;
    private Date birthdate;
    private String email;
    private String interests;
    private String friends[];
    private String friendOfs[];
    private String memberOfs[];
    private static final String DELIM = ",";


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = truncate255(name);
    }

    public void setLocation(String location) {
        this.location = truncate255(location);
    }

    public void setWebsite(String website) {
        this.website = truncate255(website);
    }

    public void setUserpic(String userpic) {
        this.userpic = truncate255(userpic);
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getWebsite() {
        return website;
    }

    public String getUserpic() {
        return userpic;
    }

    public String getInterests() {
        //TODO: implement that
        throw new RuntimeException("Not implemented yet");
//        return interests;
    }


    public String[] getFriends() {
        return friends;
    }

    public String[] getFriendOfs() {
        return friendOfs;
    }


    public String getMemberOfs() {
        //TODO: implememt that
        throw new RuntimeException("Not implemented yet");
//        return memberOfs;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }


    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

/*    public static String[] toArray(String value) {
    List<String> result = new ArrayList<String>();
    StringTokenizer tokenizer = new StringTokenizer(value, DELIM);
    while (tokenizer.hasMoreTokens()) {
        result.add(tokenizer.nextToken());
    }
    return result.toArray(new String[result.size()]);
}

public static String fromArray(String[] value) {
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < value.length; i++) {
        stringBuffer.append(value[i]);
        if (i != value.length - 1) {
            stringBuffer.append(DELIM);
        }
    }
    return stringBuffer.toString();
}*/

    private String truncate255(String value) {
        if (value == null) {
            return null;
        }
        return value.substring(0, Math.max(0, Math.min(255, value.length())));
    }

    public void setMemberOfs(String[] memberOfs) {
        this.memberOfs = memberOfs;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }

    public void setFriendOfs(String[] friendOfs) {
        this.friendOfs = friendOfs;
    }

    public String toString() {
        return "UserProfile{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                ", blog='" + blog + '\'' +
                ", userpic='" + userpic + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                ", interests=" + interests +
                ", friends=" + friends +
                ", friendOfs=" + friendOfs +
                ", memberOfs=" + memberOfs +
                '}';
    }
}
